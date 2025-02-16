package com.medilabo.msbackendpatient.service;

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

import com.medilabo.msbackendpatient.dto.PatientDTO;
import com.medilabo.msbackendpatient.exception.PatientNotFoundException;
import com.medilabo.msbackendpatient.model.Patient;
import com.medilabo.msbackendpatient.repository.PatientRepository;


public class PatientServiceTest {

	@Mock
	private PatientRepository patientRepository;
	
	@InjectMocks
	private PatientService patientService;
	
	private Patient testPatient;
	private PatientDTO testpatientDTO;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		testPatient = new Patient(1L, "John", "Doe", LocalDate.of(1985,1, 1),"Male", "123 street", "987654321");
		testpatientDTO =  new PatientDTO(1L, "John", "Doe", LocalDate.of(1985,1, 1), 38, "Male", "123 street", "987654321", 1);
	}
	
	@Test
	void testGetPatientByIdSuccess() {
		when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
		
		PatientDTO result = patientService.getPatientById(1L);
		
		assertNotNull(result);
		assertEquals("John", result.getFirstName());
		verify(patientRepository, times(1)).findById(1L);
	}
	
	@Test
	void testGetPatientByIdNotFound() {
		when(patientRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(1L));
	}
	
	@Test
	void testAddPatient() {
		when(patientRepository.save(testPatient)).thenReturn(testPatient);
		
		PatientDTO result = patientService.addPatient(testpatientDTO);
		
		assertNotNull(result);
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		verify(patientRepository, times(1)).save(testPatient);
	}
	
	@Test
	void testUpdatePatientSuccess() {
		when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
		when(patientRepository.save(testPatient)).thenReturn(testPatient);
		
		PatientDTO updatedPatientDTO = new PatientDTO(1L, "Jane", "Doe", LocalDate.of(1985,1, 1), 38, "Female", "456 street", "987654321", 1);
		
		PatientDTO result = patientService.updatePatient(1L , updatedPatientDTO);
		
		assertEquals("Jane", result.getFirstName());
		verify(patientRepository, times(1)).findById(1L);
		verify(patientRepository, times(1)).save(ArgumentMatchers.any(Patient.class));
	}
	
	@Test
	void testUpdatePatientNotFound() {
		when(patientRepository.findById(1L)).thenReturn(Optional.empty());
		
		PatientDTO updatedPatientDTO = new PatientDTO(1L, "Jane", "Doe", LocalDate.of(1985,1, 1), 38, "Female", "456 street", "987654321", 1);
		
		assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(1L, updatedPatientDTO));
	}
}
