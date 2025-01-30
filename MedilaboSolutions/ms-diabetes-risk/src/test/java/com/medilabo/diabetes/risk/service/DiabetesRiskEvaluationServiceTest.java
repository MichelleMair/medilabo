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
		
		when(patientClient.getPatientById(1)).thenReturn(patient);
		when(notesClient.getNotesByPatientId(1)).thenReturn(List.of());
		
		String riskLevel = diabetesRiskEvaluationService.evaluateRisk(1);
		
		assertEquals("None", riskLevel);
	}
}
