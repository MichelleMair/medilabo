package com.medilabo.diabetes.risk.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	private int patId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String gender;
}
