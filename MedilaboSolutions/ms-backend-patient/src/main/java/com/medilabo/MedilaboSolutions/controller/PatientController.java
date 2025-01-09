package com.medilabo.MedilaboSolutions.controller;

import java.util.List;

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

import com.medilabo.MedilaboSolutions.model.Patient;
import com.medilabo.MedilaboSolutions.service.PatientService;

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
	public ResponseEntity<String> deletePatient(@PathVariable String id) {
		patientService.deletePatient(id);
		return ResponseEntity.ok("Patient with ID: " + id + " has been deleted successfully!");
	}
}
