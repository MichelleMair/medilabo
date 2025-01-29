package com.medilabo.notes.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class NotesTest {
	
	private Validator validator;
	
	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	void testValideNotes() {
		Notes notes = new Notes("1",2,"TestPatient", "This i a valid note.");
		
		Set<ConstraintViolation<Notes>> violations = validator.validate(notes);
		assertTrue(violations.isEmpty());
	}
	
	
	@Test    
	void testInvalidNotes_NullPatient() {
		Notes notes = new Notes("1",2, null, "This i a valid note.");
		
		Set<ConstraintViolation<Notes>> violations = validator.validate(notes);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	void testInvalidNotes_NullNote() {
		Notes notes = new Notes("1", 2,"TestPatient", null);
		
		Set<ConstraintViolation<Notes>> violations = validator.validate(notes);
		assertFalse(violations.isEmpty());
	}

}
