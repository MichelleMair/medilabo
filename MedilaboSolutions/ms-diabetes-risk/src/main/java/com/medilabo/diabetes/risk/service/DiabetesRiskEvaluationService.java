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
	
	public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
		if((birthDate != null) && (currentDate != null)) {
			return Period.between(birthDate, currentDate).getYears();
		}
		return 0;
	}
	
	public String evaluateRisk(int patId) {
		Patient patient = patientClient.getPatientById(patId);
		List<Note> notes = notesClient.getNotesByPatientId(patId);
		
		int age = calculateAge(patient.getDateOfBirth(), LocalDate.now());
		System.out.println("Date actuelle: " + LocalDate.now());
		System.out.println("Date de naissance: " + patient.getDateOfBirth());
		System.out.println("Age calculé: " + age);
		
		long triggerCount = notes.stream()
				.filter(note -> note.getNote() != null)
				.flatMap(note -> TRIGGER_TERMS.stream().filter(note.getNote()::contains))
				.count();
		//Aucun risque
		if (triggerCount == 0) {
			return "None";
		}
		
		// Borderline 
		if (triggerCount >= 2 && triggerCount <= 5 && age > 30) {
			return "Borderline";
		}
		
		//Early onset
		if ((patient.getGender().equalsIgnoreCase("Male") && age < 30 && triggerCount >= 5) ||
				(patient.getGender().equalsIgnoreCase("Female") && age < 30 && triggerCount >= 7) ||
				(age > 30 && triggerCount >= 8)) {
			return "Early onset";
		}
		
		// In Danger
		if ((patient.getGender().equalsIgnoreCase("Male") && age < 30 && triggerCount >= 3) ||
			(patient.getGender().equalsIgnoreCase("Female") && age < 30 && triggerCount >= 4) ||
			(age > 30 && triggerCount >= 6 && triggerCount <= 7)) {
			return "In Danger";
		}

		
		return "None";
	}

			
}
