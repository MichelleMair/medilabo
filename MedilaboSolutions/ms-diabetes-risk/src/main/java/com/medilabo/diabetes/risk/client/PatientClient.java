package com.medilabo.diabetes.risk.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.diabetes.risk.model.Patient;

@FeignClient(name= "ms-backend-patient", url="http://ms-backend-patient:8080")
public interface PatientClient {
	@GetMapping("/api/patients/{id}")
	Patient getPatientById(@PathVariable("id") int id);
}
