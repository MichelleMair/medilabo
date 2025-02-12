package com.medilabo.msbackendpatient.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.medilabo.msbackendpatient.exception.PatientNotFoundException;
import com.medilabo.msbackendpatient.model.Patient;
import com.medilabo.msbackendpatient.model.SequenceCounter;
import com.medilabo.msbackendpatient.repository.PatientRepository;

@Service
public class PatientService {
	
	private final PatientRepository patientRepository;
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
	private final MongoOperations mongoOperations;
	
	public PatientService(PatientRepository patientRepository, MongoOperations mongoOperations) {
		this.patientRepository = patientRepository;
		this.mongoOperations = mongoOperations;
	}
	
	

	/**
	 * Generates the next sequence value for custom patient IDS  (patId)
	 * @param sequenceName The name of the sequence
	 * @return the next sequence value
	 */
	public int getNextSequenceValue(String sequenceName) {
		Query query = new Query(Criteria.where("_id").is(sequenceName));
		Update update = new Update().inc("sequenceValue", 1);
		FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
		SequenceCounter counter = mongoOperations.findAndModify(query,  update,  options, SequenceCounter.class);
		
		if(counter == null) {
			counter = new SequenceCounter(sequenceName, 1);
			mongoOperations.save(counter);
		}
		
		return counter.getSequenceValue();
	}
	
	
	/**
	 * Calculates the age of a patient based on the date of birth
	 * @param birthDate The date of birth of the patient
	 * @return the age in years
	 */
	public int calculateAge(LocalDate birthDate) {
		if(birthDate == null) {
			return 0;
		}
		return Period.between(birthDate, LocalDate.now()).getYears();
	}
	
	//Get all patients
	public List<Patient> getAllPatients() {
		List<Patient> patients = patientRepository.findAll();
		patients.forEach(patient -> {
			patient.setAge(calculateAge(patient.getDateOfBirth()));
		});
		return patients;
	}
	
	//Get patient by id
	public Patient getPatientById(String id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
	}
	
	//Get patient by patId
	public Patient getPatientByPatId(int patId) {
		return patientRepository.findByPatId(patId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found wit patId: " + patId));
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
		// Generate custom ID for the patient
		patient.setPatId(getNextSequenceValue("patients"));
		logger.info("Adding patient : {}", patient);
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
		
		logger.info("Finding patient by ID : {}", existingPatient);
		
		patientRepository.delete(existingPatient);
	}

}
