package com.medilabo.msbackendpatient.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private int age;
	private String gender;
	private String address;
	private String phoneNumber;
	private int patId;
	
	public PatientDTO(Long id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String address, String phoneNumber, int patId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.patId = patId;
	}

}
