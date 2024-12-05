package com.medilabo.MedilaboSolutions.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.MedilaboSolutions.model.Patient;
import com.medilabo.MedilaboSolutions.repository.PatientRepository;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Patient testPatient;
	
	
	@BeforeEach
	void setUp() {
		patientRepository.deleteAll();
		
		testPatient = new Patient();
		
		testPatient.setFirstName("John");
		testPatient.setLastName("Doe");
		testPatient.setDateOfBirth(LocalDate.of(1985,1, 1));
		testPatient.setGender("Male");
		patientRepository.save(testPatient);
	}
	
	@Test
	void testGetAllPatients() throws Exception {
		mockMvc.perform(get("/api/patients")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].firstName").value("John"));
	}
	
	@Test
	void testGetPatientById() throws Exception {
		mockMvc.perform(get("/api/patients/" + testPatient.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John"));
	}
	
}
