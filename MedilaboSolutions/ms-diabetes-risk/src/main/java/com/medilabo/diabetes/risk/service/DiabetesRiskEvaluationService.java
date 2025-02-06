package com.medilabo.diabetes.risk.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.medilabo.diabetes.risk.client.NotesClient;
import com.medilabo.diabetes.risk.client.PatientClient;
import com.medilabo.diabetes.risk.model.Note;
import com.medilabo.diabetes.risk.model.Patient;
import com.medilabo.diabetes.risk.utils.TriggerTerms;

@Service
public class DiabetesRiskEvaluationService {

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
		Patient patient = patientClient.getPatientByPatId(patId);
		List<Note> notes = notesClient.getNotesByPatientId(patId);
		
		int age = calculateAge(patient.getDateOfBirth(), LocalDate.now());
		
		long triggerCount = notes.stream()
				.filter(note -> note.getNote() != null)
				.map(note -> {
					String noteContentLower = note.getNote().toLowerCase();
					
					long count = TriggerTerms.TERMS.stream()
							.filter(trigger -> {
								// Utilisation de regex pour détecter les termes déclencheurs stricts avec l'acceptation des majuscules et minuscules
								Pattern pattern = Pattern.compile("\\b" + Pattern.quote(trigger.toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
								boolean found = pattern.matcher(noteContentLower).find();
								return found;
							})
							.count();
				return count;
				})
				.reduce(0L, Long::sum);
		
		//Aucun risque
		if (triggerCount == 0) {
			return "None";
		}
		
		// Borderline 
		if (triggerCount >= 2 && triggerCount <= 5 && age > 30) {
			return "Borderline";
		}
		
		
		// In Danger
		if ((patient.getGender().equalsIgnoreCase("Male") && age < 30 && triggerCount >= 3 && triggerCount < 5) ||
			(patient.getGender().equalsIgnoreCase("Male") && age >= 30 && triggerCount >= 6 && triggerCount < 8) ||
			(patient.getGender().equalsIgnoreCase("Female") && age < 30 && triggerCount >= 4 && triggerCount < 7) ||
			(patient.getGender().equalsIgnoreCase("Female") && age >= 30 && triggerCount >= 6 && triggerCount <= 7)) {
			return "In Danger";
		}

		
		//Early onset
		if ((patient.getGender().equalsIgnoreCase("Male") && age < 30 && triggerCount >= 5) ||
				(patient.getGender().equalsIgnoreCase("Female") && age < 30 && triggerCount >= 7) ||
				(age > 30 && triggerCount >= 8)) {
			return "Early onset";
		}

		
		return "None";
	}

			
}
