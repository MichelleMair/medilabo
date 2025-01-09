package com.medilabo.MedilaboSolutions.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.medilabo.MedilaboSolutions.exception.PatientNotFoundException;
import com.medilabo.MedilaboSolutions.model.Patient;
import com.medilabo.MedilaboSolutions.repository.PatientRepository;

@Service
public class PatientService {
	
	private final PatientRepository patientRepository;
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
	
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	//Get all patients
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}
	
	//Get patient by id
	public Patient getPatientById(String id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
	}
	
	//add new patient
	public Patient addPatient(Patient patient) {
		boolean exists = patientRepository.existsByFirstNameAndLastNameAndDateOfBirth(
				patient.getFirstName(), 
				patient.getLastName(),
				patient.getDateOfBirth());
		if (exists) {
			throw new IllegalArgumentException("A patient with the same name and date of birth already exists.");
		}
		
		logger.info("Adding patient : {}" + patient);
		return patientRepository.save(patient);
	}
	
	//Update an existing patient
	public Patient updatePatient (String id, Patient patientDetails) {
		//find patient by id 
		Patient existingPatient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
		
		//Update patient details if found
		existingPatient.setFirstName(patientDetails.getFirstName());
		existingPatient.setLastName(patientDetails.getLastName());
		existingPatient.setDateOfBirth(patientDetails.getDateOfBirth());
		existingPatient.setGender(patientDetails.getGender());
		existingPatient.setAddress(patientDetails.getAddress());
		existingPatient.setPhoneNumber(patientDetails.getPhoneNumber());
		
		logger.info("Updating patient : {}" + existingPatient);
		//Save modifications to database
		Patient updatedPatient = patientRepository.save(existingPatient);
		
		//return the updated patient
		return updatedPatient; 
	}
	
	//Delete patient by id
	public void deletePatient(String id) {
		Patient existingPatient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
		
		logger.info("Finding patient by ID : {}" + existingPatient);
		
		patientRepository.delete(existingPatient);
	}

}
