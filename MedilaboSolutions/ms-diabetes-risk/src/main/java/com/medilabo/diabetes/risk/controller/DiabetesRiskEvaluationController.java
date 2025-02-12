package com.medilabo.diabetes.risk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.diabetes.risk.service.DiabetesRiskEvaluationService;

@RestController
@RequestMapping("/api/risk")
public class DiabetesRiskEvaluationController {

	private final DiabetesRiskEvaluationService diabetesRiskEvaluationService;
	
	//Constructor for DiabetesRiskEvaluationController
	public DiabetesRiskEvaluationController (DiabetesRiskEvaluationService diabetesRiskEvaluationService) {
		this.diabetesRiskEvaluationService = diabetesRiskEvaluationService;
	}
	
	/**
	 * Evaluates the diabetes risk level for a patient by their id
	 * 
	 * @param id The patient ID
	 * @return The risk level as a string 
	 */
	@GetMapping("/{id}")
	public String getRiskLevel(@PathVariable int id) {
		return diabetesRiskEvaluationService.evaluateRisk(id);
	}
}
