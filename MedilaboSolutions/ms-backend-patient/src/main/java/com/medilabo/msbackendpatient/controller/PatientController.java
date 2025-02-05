package com.medilabo.msbackendpatient.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.msbackendpatient.model.Patient;
import com.medilabo.msbackendpatient.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	//Get list of all patients
	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}
	
	
	//Get a patient by id
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
			Patient patient = patientService.getPatientById(id);
			return ResponseEntity.ok(patient);
	}
	
	//Get patient by patId
	@GetMapping("/patId/{patId}")
	public ResponseEntity<Patient> getPatientByPatId(@PathVariable int patId) {
		Patient patient = patientService.getPatientByPatId(patId);
		return ResponseEntity.ok(patient);
	}
	
	//Add new patient
	@PostMapping
	public ResponseEntity<Patient> createPatient(@Validated @RequestBody Patient patient) {
		Patient newPatient = patientService.addPatient(patient);
		return ResponseEntity.ok(newPatient);
	}
	
	//Updated existing patient 
	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable String id, @Validated @RequestBody Patient patientDetails) {
		Patient udpatedPatient = patientService.updatePatient(id, patientDetails);
		return ResponseEntity.ok(udpatedPatient);
	}
	
	//Delete patient by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deletePatient(@PathVariable String id) {
		patientService.deletePatient(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Patient with ID: " + id + " has been deleted successfully!");
		return ResponseEntity.ok(response);
	}
}
