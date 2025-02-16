package com.medilabo.msbackendpatient.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name="patients")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message= "First name is required")
	@Column(name= "first_name", nullable= false)
	private String firstName;
	
	@NotBlank(message= "Last name is required")
	@Column(name= "last_name", nullable= false)
	private String lastName;
	
	@NotNull(message= "Date of birth is required")
	@Past(message= "Date of birth must be in the past")
	@Column(name= "date_of_birth", nullable= false)
	private LocalDate dateOfBirth;
	
	
	@Pattern(regexp= "^(Male|Female|Other)$", message= "Gender must be 'Male', 'Female', or 'Other'.")
	@NotBlank(message= "Gender is required")
	@Column(name= "gender", nullable= false)
	private String gender;
	
	@Column(name= "address")
	private String address;
	
	@Pattern(regexp= "^\\+?[0-9\\-\\s]*$", message= "Phone number must be valid")
	@Column(name= "phone_number")
	private String phoneNumber;
	
}
