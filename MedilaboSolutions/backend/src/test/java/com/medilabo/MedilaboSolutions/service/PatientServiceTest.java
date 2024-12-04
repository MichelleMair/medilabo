package com.medilabo.MedilaboSolutions.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.medilabo.MedilaboSolutions.exception.PatientNotFoundException;
import com.medilabo.MedilaboSolutions.model.Patient;
import com.medilabo.MedilaboSolutions.repository.PatientRepository;

public class PatientServiceTest {

	@Mock
	private PatientRepository patientRepository;
	
	@InjectMocks
	private PatientService patientService;
	
	private Patient testPatient;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		testPatient = new Patient();
		testPatient.setId("123");
		testPatient.setFirstName("John");
		testPatient.setLastName("Doe");
		testPatient.setDateOfBirth(LocalDate.of(1985,1, 1));
		testPatient.setGender("Male");
	}
	
	@Test
	void testGetPatientByIdSuccess() {
		when(patientRepository.findById("123")).thenReturn(Optional.of(testPatient));
		
		Patient result = patientService.getPatientById("123");
		
		assertNotNull(result);
		assertEquals("John", result.getFirstName());
		verify(patientRepository, times(1)).findById("123");
	}
	
	@Test
	void testGetPatientByIdNotFound() {
		when(patientRepository.findById("123")).thenReturn(Optional.empty());
		
		assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById("123"));
	}
}
