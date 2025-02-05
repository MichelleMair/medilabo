package com.medilabo.msbackendpatient.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.msbackendpatient.model.Patient;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
	boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
	
	Optional<Patient> findByPatId(int patId);
}
