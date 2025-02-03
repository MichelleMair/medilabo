package com.medilabo.diabetes.risk.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.medilabo.diabetes.risk.client.NotesClient;
import com.medilabo.diabetes.risk.client.PatientClient;
import com.medilabo.diabetes.risk.service.DiabetesRiskEvaluationService;

@WebMvcTest(DiabetesRiskEvaluationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DiabetesRiskEvaluationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DiabetesRiskEvaluationService diabetesRiskEvaluationService;
	
	@MockBean
	private PatientClient patientClient;
	
	@MockBean
	private NotesClient notesClient;
	
	@Test
	void testGetRiskLevel() throws Exception {
		when(diabetesRiskEvaluationService.evaluateRisk(1)).thenReturn("Borderline");
		
		mockMvc.perform(get("/api/risk/1"))
		.andExpect(status().isOk())
		.andExpect(content().string("Borderline"));
	}
}
