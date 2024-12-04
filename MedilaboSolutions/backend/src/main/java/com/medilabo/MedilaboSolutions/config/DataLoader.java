package com.medilabo.MedilaboSolutions.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.medilabo.MedilaboSolutions.model.Patient;
import com.medilabo.MedilaboSolutions.repository.PatientRepository;

@Configuration
public class DataLoader {

	@Bean
	public CommandLineRunner loadData(PatientRepository patientRepository) {
		return args -> {
		//check if data exists 
			if (patientRepository.count() == 0) {
				//Add data
				patientRepository.save(new Patient(null, "Test", "TestNone", LocalDate.of(1966, 12, 31), "F", "1 Brookside St", "100-222-3333"));
				patientRepository.save(new Patient(null, "Test", "TestBorderline", LocalDate.of(1945, 06, 24), "M", "2 High St", "200-333-4444"));
				patientRepository.save(new Patient(null, "Test", "TestInDanger", LocalDate.of(2004, 06, 18), "M", "3 Club Road", "300-444-5555"));
				patientRepository.save(new Patient(null, "Test", "TestEarlyOnset", LocalDate.of(2002, 06, 28), "F", "4 Valley Dr", "400-555-6666"));
				System.out.println("Data loaded into MongoDB!");
			} else {
				System.out.println("Data already exists in MongoDB!");
			}
		};
	}
}
