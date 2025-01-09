package com.medilabo.MedilaboSolutions.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.medilabo.MedilaboSolutions.model.Patient;

public interface PatientRepository extends MongoRepository<Patient, String> {
	boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
}
