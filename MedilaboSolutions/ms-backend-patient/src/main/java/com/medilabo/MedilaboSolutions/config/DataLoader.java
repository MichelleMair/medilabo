package com.medilabo.MedilaboSolutions.config;

//@Configuration
public class DataLoader {

	/*
	 * Please follow instructions in README file
	 * Enable this class to insert initial data to MongoDB database
	 * Once data has bee successfully insert to database
	 * please disable this class to avoid reinserting data on every app startup
	 * 
	 */
	/*
	@Bean
	public CommandLineRunner loadData(PatientRepository patientRepository, PatientService patientService) {
		return args -> {
		//check if data exists 
			if (patientRepository.count() == 0) {
				//Add data
                patientRepository.save(new Patient(null, "Test", "TestNone", LocalDate.of(1966, 12, 31), "Female", "1 Brookside St", "100-222-3333", patientService.getNextSequenceValue("patients")));
                patientRepository.save(new Patient(null, "Test", "TestBorderline",LocalDate.of(1945, 6, 24),"Male", "2 High St", "200-333-4444", patientService.getNextSequenceValue("patients")));
                patientRepository.save(new Patient(null, "Test", "TestInDanger", LocalDate.of(2004, 6, 18), "Male", "3 Club Road", "300-444-5555", patientService.getNextSequenceValue("patients")));
                patientRepository.save(new Patient(null, "Test", "TestEarlyOnset", LocalDate.of(2002, 6, 28), "Female", "4 Valley Dr", "400-555-6666", patientService.getNextSequenceValue("patients")));
				System.out.println("Data loaded into MongoDB!");
			} else {
				System.out.println("Data already exists in MongoDB!");
			}
		};
	}
	*/
}
