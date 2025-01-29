package com.medilabo.MedilaboSolutions.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "counters")
public class SequenceCounter {

	@Id
	private String id;
	private int sequenceValue;
	
	
}
