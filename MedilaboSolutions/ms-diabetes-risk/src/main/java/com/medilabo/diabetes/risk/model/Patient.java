package com.medilabo.diabetes.risk.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Patient {
	private int patId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String gender;
}
