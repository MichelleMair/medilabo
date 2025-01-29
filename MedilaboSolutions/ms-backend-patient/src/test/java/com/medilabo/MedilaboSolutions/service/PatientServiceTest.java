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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.medilabo.MedilaboSolutions.exception.PatientNotFoundException;
import com.medilabo.MedilaboSolutions.model.Patient;
import com.medilabo.MedilaboSolutions.model.SequenceCounter;
import com.medilabo.MedilaboSolutions.repository.PatientRepository;


public class PatientServiceTest {

	@Mock
	private PatientRepository patientRepository;
	
	@Mock
	private MongoOperations mongoOperations;
	
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
	
	@Test
	void testAddPatient() {
		when(mongoOperations.findAndModify(
				ArgumentMatchers.any(Query.class), 
				ArgumentMatchers.any(Update.class), 
				ArgumentMatchers.any(FindAndModifyOptions.class),
				ArgumentMatchers.eq(SequenceCounter.class)))
				.thenReturn(new SequenceCounter("patients", 1));
		
		when(patientRepository.save(ArgumentMatchers.any(Patient.class))).thenReturn(testPatient);
		
		Patient result = patientService.addPatient(testPatient);
		
		assertNotNull(result);
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		verify(patientRepository, times(1)).save(testPatient);
	}
	
	@Test
	void testUpdatePatientSuccess() {
		when(patientRepository.findById("123")).thenReturn(Optional.of(testPatient));
		when(patientRepository.save(ArgumentMatchers.any(Patient.class))).thenReturn(testPatient);
		
		Patient updatedPatient = new Patient();
		updatedPatient.setFirstName("Jane");
		
		Patient result = patientService.updatePatient("123", updatedPatient);
		
		assertEquals("Jane", result.getFirstName());
		verify(patientRepository, times(1)).findById("123");
		verify(patientRepository, times(1)).save(ArgumentMatchers.any(Patient.class));
	}
	
	@Test
	void testUpdatePatientNotFound() {
		when(patientRepository.findById("123")).thenReturn(Optional.empty());
		
		Patient updatedPatient = new Patient();
		updatedPatient.setFirstName("Jane");
		
		assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient("123", updatedPatient));
	}
}
