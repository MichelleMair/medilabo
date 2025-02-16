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

import com.medilabo.msbackendpatient.dto.PatientDTO;
import com.medilabo.msbackendpatient.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	
	
	/**
	 * Get list of all patients
	 * @return ResponseEntity containing the list of patients
	 */
	@GetMapping
	public ResponseEntity<List<PatientDTO>> getAllPatients() {
		List<PatientDTO> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}
	
	
	/**
	 * Get a patient by id
	 * @param id The ID of the patient
	 * @return ResponseEntity containing patient's details
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
			PatientDTO patient = patientService.getPatientById(id);
			return ResponseEntity.ok(patient);
	}
	
	/**
	 * Get patient by patId
	 * @param patId The patID 
	 * @return ResponseEntity containing patient's details
	 */
	@GetMapping("/patId/{patId}")
	public ResponseEntity<PatientDTO> getPatientByPatId(@PathVariable int patId) {
		PatientDTO patientDTO = patientService.getPatientByPatId(patId);
		return ResponseEntity.ok(patientDTO);
	}
	
	/**
	 * Add new patient to the database
	 * 
	 * @param patient Patient's details to be added
	 * @return ResponseEntity containing the newly added patient
	 */
	@PostMapping
	public ResponseEntity<PatientDTO> createPatient(@Validated @RequestBody PatientDTO patientDTO) {
		PatientDTO newPatient = patientService.addPatient(patientDTO);
		return ResponseEntity.ok(newPatient);
	}
	
	
	/**
	 * Updates an existing patient's details
	 * @param id the ID of the patient to be updated
	 * @param patientDetails The updated patient details
	 * @return ResponseEntity containing the updated patient
	 */
	@PutMapping("/{id}")
	public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @Validated @RequestBody PatientDTO patientDTO) {
		PatientDTO udpatedPatient = patientService.updatePatient(id, patientDTO);
		return ResponseEntity.ok(udpatedPatient);
	}
	
	
	/**
	 * Delete patient by id
	 * 
	 * @param id The ID of the patient to be deleted
	 * @return ResponseEntity containing a confirmation message
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deletePatient(@PathVariable Long id) {
		patientService.deletePatient(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Patient with ID: " + id + " has been deleted successfully!");
		return ResponseEntity.ok(response);
	}
}
