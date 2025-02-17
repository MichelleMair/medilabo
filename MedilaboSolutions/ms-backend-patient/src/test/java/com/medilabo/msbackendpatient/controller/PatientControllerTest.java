package com.medilabo.msbackendpatient.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.msbackendpatient.config.test.TestSecurityConfig;
import com.medilabo.msbackendpatient.dto.PatientDTO;
import com.medilabo.msbackendpatient.repository.PatientRepository;
import com.medilabo.msbackendpatient.service.PatientService;

@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = PatientController.class)
@ActiveProfiles("test")
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PatientService patientService;
	
	@MockBean
	private PatientRepository patientRepository;
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private PatientDTO patient1;
	private PatientDTO patient2;
	private List<PatientDTO> patients;
	
	@BeforeEach
	void setUp() {
		
		patient1 = new PatientDTO (1L, "John", "Doe", LocalDate.of(1985, 1,1), 38, "Male", "123 street", "987654321", 1);
		patient2 = new PatientDTO (2L, "Jane", "Doe", LocalDate.of(1990, 2,1), 33, "Female", "456 av street", "123456789", 2);
		patients = List.of(patient1, patient2);
	}
	
	@Test
	@DisplayName("Get all patients should return a list of patients")
	@WithMockUser(username= "user", roles= {"USER"})
	void testGetAllPatients() throws Exception {
		when(patientService.getAllPatients()).thenReturn(patients);
		
		mockMvc.perform(get("/api/patients"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].firstName").value("John"))
			.andExpect(jsonPath("$[1].firstName").value("Jane"));
		
		verify(patientService, times(1)).getAllPatients();
	}
	
	@Test
	@DisplayName("Get patient by ID should return the patient")
	@WithMockUser(username= "user", roles= {"USER"})
	void testGetPatientById() throws Exception {
		
		when(patientService.getPatientById(1L)).thenReturn(patient1);
		
		mockMvc.perform(get("/api/patients/{id}" , 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Doe"));
		
		verify(patientService, times(1)).getPatientById(1L);
	}
	
	@Test
	@DisplayName("Get patient by patId should return the patient")
	@WithMockUser(username= "user", roles= {"USER"})
	void testGetPatientByPatId() throws Exception {
		
		when(patientService.getPatientByPatId(1)).thenReturn(patient1);
		
		mockMvc.perform(get("/api/patients/patId/{patId}" , 1))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John"));
		
		verify(patientService, times(1)).getPatientByPatId(1);
	}
	
	
	@Test
	@DisplayName("Add new patient shoudl return the created patient")
	@WithMockUser(username= "admin", roles= {"ADMIN"})
	void testAddPatient() throws Exception {
		when(patientService.addPatient(Mockito.any(PatientDTO.class))).thenReturn(patient1);
		
		mockMvc.perform(post("/api/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patient1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John"));
		
		verify(patientService, times(1)).addPatient(Mockito.any(PatientDTO.class));
	}
	
	@Test
	@DisplayName("Update patient should return the updated patient")
	@WithMockUser(username= "admin", roles= {"ADMIN"})
	void testUpdatePatient() throws Exception {

		when(patientService.updatePatient(eq(2L), Mockito.any(PatientDTO.class))).thenReturn(patient2);
		
		mockMvc.perform(put("/api/patients/{id}", 2L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patient2)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Jane"))
				.andExpect(jsonPath("$.gender").value("Female"));
		
		verify(patientService, times(1)).updatePatient(eq(2L), Mockito.any(PatientDTO.class));
	}
	
	@Test
	@DisplayName("Delete patient should return no content")
	@WithMockUser(username= "admin", roles= {"ADMIN"})
	void testDeletePatient() throws Exception{
		
		doNothing().when(patientService).deletePatient(1L);
		
		mockMvc.perform(delete("/api/patients/{id}", 1L))
				.andExpect(status().isOk());
		
		verify(patientService, times(1)).deletePatient(1L);
	}
	
}
