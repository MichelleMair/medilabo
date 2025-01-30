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
	
	public DiabetesRiskEvaluationController (DiabetesRiskEvaluationService diabetesRiskEvaluationService) {
		this.diabetesRiskEvaluationService = diabetesRiskEvaluationService;
	}
	
	@GetMapping("/{id}")
	public String getRiskLevel(@PathVariable int id) {
		return diabetesRiskEvaluationService.evaluateRisk(id);
	}
}
