package com.medilabo.diabetes.risk.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	private String id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private int age;
	private String gender;
	private String address;
	private String phoneNumber;
	private int patId;
}
