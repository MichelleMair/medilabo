package com.medilabo.MedilaboSolutions.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "patients")
public class Patient {

	@Id
	private String id;
	
	@NotBlank(message= "First name is required")
	private String firstName;
	
	@NotBlank(message= "Last name is required")
	private String lastName;
	
	@NotNull(message= "Date of birth is required")
	@Past(message= "Date of birth must be in the past")
	private LocalDate dateOfBirth;
	
	@Transient
	private int age;
	
	@Pattern(regexp= "^(Male|Female|Other)$", message= "Gender must be 'Male', 'Female', or 'Other'. ")
	private String gender;
	
	private String address;
	
	@Pattern(regexp= "^\\+?[0-9\\-\\s]+$", message= "Phone number must be valid")
	private String phoneNumber;
	
	public Patient(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String address, String phoneNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
}
