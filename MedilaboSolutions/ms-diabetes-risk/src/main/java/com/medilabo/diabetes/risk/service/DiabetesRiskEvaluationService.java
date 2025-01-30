package com.medilabo.diabetes.risk.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.diabetes.risk.client.NotesClient;
import com.medilabo.diabetes.risk.client.PatientClient;
import com.medilabo.diabetes.risk.model.Note;
import com.medilabo.diabetes.risk.model.Patient;

@Service
public class DiabetesRiskEvaluationService {

	private static final List<String> TRIGGER_TERMS = List.of(
			"Hémoglobine A1C",
			"Microalbumine",
			"Taille",
			"Poids",
			"Fumeur", 
			"Fumeuse",
			"Anormal",
			"Cholestérol",
			"Vertiges",
			"Rechute",
			"Réaction",
			"Anticorps"
	);
	
	private final PatientClient patientClient;
	private final NotesClient notesClient;
	
	public DiabetesRiskEvaluationService(PatientClient patientClient, NotesClient notesClient) {
		this.patientClient = patientClient;
		this.notesClient = notesClient;
	}
	
	public String evaluateRisk(int patId) {
		Patient patient = patientClient.getPatientById(patId);
		List<Note> notes = notesClient.getNotesByPatientId(patId);
		
		int age = Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears();
		long triggerCount = notes.stream()
				.filter(note -> note.getNote() != null)
				.flatMap(note -> TRIGGER_TERMS.stream().filter(note.getNote()::contains))
				.count();
		if (triggerCount == 0) {
			return "None";
		}
		if (triggerCount >= 2 && triggerCount <= 5 && age > 30) {
			return "Borderline";
		}
		if ((patient.getGender().equals("Male") && age < 30 && triggerCount >= 3) ||
			(patient.getGender().equals("Female") && age < 30 && triggerCount >= 4) ||
			(age > 30 && triggerCount >= 6 && triggerCount <= 7)) {
			return "In Danger";
		}
		if ((patient.getGender().equals("Male") && age < 30 && triggerCount >= 5) ||
				(patient.getGender().equals("Female") && age < 30 && triggerCount >= 7) ||
				(age > 30 && triggerCount >= 8)) {
			return "Early onset";
		}
		
		return "None";
	}

			
}
