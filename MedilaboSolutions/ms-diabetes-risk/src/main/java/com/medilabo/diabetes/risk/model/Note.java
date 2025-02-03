package com.medilabo.diabetes.risk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

	private String id;
	private int patId;
	private String note;
}
