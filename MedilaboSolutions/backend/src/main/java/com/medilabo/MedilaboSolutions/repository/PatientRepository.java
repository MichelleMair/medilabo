package com.medilabo.MedilaboSolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.medilabo.MedilaboSolutions.model.Patient;

public interface PatientRepository extends MongoRepository<Patient, String> {

}
