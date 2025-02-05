package com.medilabo.diabetes.risk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.medilabo.diabetes.risk.client.NotesClient;
import com.medilabo.diabetes.risk.client.PatientClient;
import com.medilabo.diabetes.risk.model.Note;
import com.medilabo.diabetes.risk.model.Patient;

public class DiabetesRiskEvaluationServiceTest {

	@Mock
	private PatientClient patientClient;
	
	@Mock
	private NotesClient notesClient;
	
	@InjectMocks
	private DiabetesRiskEvaluationService diabetesRiskEvaluationService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testEvaluateRisk_None() {
		Patient patient = new Patient();
		patient.setPatId(1);
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setDateOfBirth(LocalDate.of(1990, 2, 1));
		patient.setGender("Male");
		
		when(patientClient.getPatientByPatId(1)).thenReturn(patient);
		when(notesClient.getNotesByPatientId(1)).thenReturn(List.of());
		
		String riskLevel = diabetesRiskEvaluationService.evaluateRisk(1);
		
		assertEquals("None", riskLevel);
	}
	
	@Test
	void testEvaluateRisk_Borderline() {
		Patient patient = new Patient();
		patient.setPatId(2);
		patient.setDateOfBirth(LocalDate.of(1980, 1, 1)); //44 ans 
		patient.setGender("Male");
		
		List<Note> notes = List.of(
				new Note("1", 2, "Hémoglobine A1C"),
				new Note("2", 2, "Microalbumine"),
				new Note("3", 2, "Poids")
		);
		
		when(patientClient.getPatientByPatId(2)).thenReturn(patient);
		when(notesClient.getNotesByPatientId(2)).thenReturn(notes);
		
		String riskLevel = diabetesRiskEvaluationService.evaluateRisk(2);
		assertEquals("Borderline", riskLevel);
	}
	
	@Test
	void testEvaluateRisk_InDanger() {
		Patient patient = new Patient();
		patient.setPatId(3);
		patient.setDateOfBirth(LocalDate.of(1980, 1, 1)); // 45 ans 
		patient.setGender("Male");
		
		List<Note> notes = List.of(
				new Note("1", 3, "Fumeur"),
				new Note("2", 3, "Cholestérol"),
				new Note("3", 3, "Anormal"),
				new Note("4", 3, "Vertiges"),
				new Note("5", 3, "Rechute"),
				new Note("6", 3, "Anticorps")
		);
		
		when(patientClient.getPatientByPatId(3)).thenReturn(patient);
		when(notesClient.getNotesByPatientId(3)).thenReturn(notes);
		
		String riskLevel = diabetesRiskEvaluationService.evaluateRisk(3);
		assertEquals("In Danger", riskLevel);
	}
	
	@Test
	void testEvaluateRisk_EarlyOnset() {
		Patient patient = new Patient();
		patient.setPatId(4);
		patient.setDateOfBirth(LocalDate.of(1996, 1, 1)); // 29 ans 
		patient.setGender("Male");
		
		List<Note> notes = List.of(
				new Note("1", 4, "Fumeur"),
				new Note("2", 4, "Cholestérol"),
				new Note("3", 4, "Anormal"),
				new Note("4", 4, "Vertiges"),
				new Note("5", 4, "Anticorps")
				
		);
		
		when(patientClient.getPatientByPatId(4)).thenReturn(patient);
		when(notesClient.getNotesByPatientId(4)).thenReturn(notes);
		
		String riskLevel = diabetesRiskEvaluationService.evaluateRisk(4);
		assertEquals("Early onset", riskLevel);
	}
}
