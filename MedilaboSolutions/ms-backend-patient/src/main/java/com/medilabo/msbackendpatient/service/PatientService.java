package com.medilabo.msbackendpatient.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.medilabo.msbackendpatient.dto.PatientDTO;
import com.medilabo.msbackendpatient.exception.PatientNotFoundException;
import com.medilabo.msbackendpatient.model.Patient;
import com.medilabo.msbackendpatient.repository.PatientRepository;

@Service
public class PatientService {
	
	private final PatientRepository patientRepository;
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

	
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	//Get all patients
	public List<PatientDTO> getAllPatients() {
		return patientRepository.findAll().stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	//Get patient by id
	public PatientDTO getPatientById(Long id) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
		return convertToDTO(patient);
	}
	
	//Get patient by patId
	public PatientDTO getPatientByPatId(int patId) {
		List<Patient> patients = patientRepository.findAll();
		
		return patients.stream()
				.map(this::convertToDTO) //convertir en DTO avec le bon patId calculé
				.filter(patientDTO -> patientDTO.getPatId() == patId)
				.findFirst()
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with patId: " + patId));
	}
	
	//add new patient
	public PatientDTO addPatient(PatientDTO patientDTO) {
		boolean exists = patientRepository.existsByFirstNameAndLastNameAndDateOfBirth(
				patientDTO.getFirstName(), 
				patientDTO.getLastName(),
				patientDTO.getDateOfBirth());
		if (exists) {
			throw new IllegalArgumentException("A patient with the same name and date of birth already exists.");
		}
		Patient patient = convertToEntity(patientDTO);
		Patient savedpatient = patientRepository.save(patient);
		logger.info("Adding patient : {}", savedpatient);
		return convertToDTO(savedpatient);
	}
	
	//Update an existing patient
	public PatientDTO updatePatient (Long id, PatientDTO patientDTO) {
		//find patient by id 
		Patient existingPatient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
		
		//Update patient details if found
		existingPatient.setFirstName(patientDTO.getFirstName());
		existingPatient.setLastName(patientDTO.getLastName());
		existingPatient.setDateOfBirth(patientDTO.getDateOfBirth());
		existingPatient.setGender(patientDTO.getGender());
		existingPatient.setAddress(patientDTO.getAddress());
		existingPatient.setPhoneNumber(patientDTO.getPhoneNumber());
		
		logger.info("Updating patient : {}" + existingPatient);
		//Save modifications to database
		Patient updatedPatient = patientRepository.save(existingPatient);
		
		//return the updated patient
		return convertToDTO(updatedPatient); 
	}
	
	//Delete patient by id
	public void deletePatient(Long id) {
		Patient existingPatient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
		
		logger.info("Finding patient by ID : {}", existingPatient);
		
		patientRepository.delete(existingPatient);
	}
	
	//Generate patId with patient id
	private int getNextPatId(Patient patient) {
		return patient.getId().intValue();	
	}
	
	
	
	//Calculer l'âge du patient
	private int calculateAge(LocalDate birthDate) {
		if(birthDate == null) {
			return 0;
		}
		return Period.between(birthDate, LocalDate.now()).getYears();
	}
	
	//Convert Patient to DTO
	private PatientDTO convertToDTO(Patient patient) {
		int age = calculateAge(patient.getDateOfBirth());
		return new PatientDTO(
			patient.getId(),
			patient.getFirstName(),
			patient.getLastName(),
			patient.getDateOfBirth(),
			age,
			patient.getGender(),
			patient.getAddress(),
			patient.getPhoneNumber(),
			patient.getId().intValue() //Using patient id as patId
		);		
	}
	
	//Convert PatientDTO to Patient
	private Patient convertToEntity(PatientDTO patientDTO) {
		return new Patient (
				patientDTO.getId(),
				patientDTO.getFirstName(),
				patientDTO.getLastName(),
				patientDTO.getDateOfBirth(),
				patientDTO.getGender(),
				patientDTO.getAddress(),
				patientDTO.getPhoneNumber()
		);
	}

}
