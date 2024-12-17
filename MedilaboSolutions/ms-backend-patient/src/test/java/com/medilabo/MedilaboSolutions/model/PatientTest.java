package com.medilabo.MedilaboSolutions.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class PatientTest {

	@Test
	public void testPatientCreation() {
		Patient patient = new Patient ("1", "John", "Doe", LocalDate.of(1985, 1,1), "Male", "123 street", "987654321");
		
		assertNotNull(patient);
		assertEquals("John", patient.getFirstName());
		assertEquals("Doe", patient.getLastName());
		assertEquals("Male", patient.getGender());
		assertEquals("123 street", patient.getAddress());
	}
}
