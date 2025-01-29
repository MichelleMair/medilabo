package com.medilabo.notes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection= "notes")
public class Notes {

	@Id
	private String id;
	
	@NotNull(message= "Patient ID is required")
	private int patId;
	
	@NotNull(message= "Patient name is required")
	private String patient;
	
	@NotNull(message= "Note content is required")
	private String note;	
	
}
