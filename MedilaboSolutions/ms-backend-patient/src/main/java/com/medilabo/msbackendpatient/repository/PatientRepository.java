package com.medilabo.msbackendpatient.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.medilabo.msbackendpatient.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
	
	@Query("SELECT MAX(p.id) FROM Patient p")
	Integer findMaxPatId();
}
