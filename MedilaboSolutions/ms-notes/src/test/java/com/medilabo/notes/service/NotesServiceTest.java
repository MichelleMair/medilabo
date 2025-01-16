package com.medilabo.notes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.medilabo.notes.model.Notes;
import com.medilabo.notes.repository.NotesRepository;

public class NotesServiceTest {
	
	private NotesRepository notesRepository;
	private NotesService notesService;
	
	@BeforeEach
	void setUp() {
		notesRepository = mock(NotesRepository.class);
		notesService = new NotesService(notesRepository);
	}

	@Test
	void testGetAllNotesByPatientId() {
		List<Notes> notesList = Arrays.asList(
				new Notes("1","1","TestPatient", "First note."),
				new Notes("2","1","TestPatient", "Second note.")
		);
		
		when(notesRepository.findByPatId("1")).thenReturn(notesList);
		
		List<Notes> result = notesService.getNotesByPatientId("1");
		assertEquals(2, result.size());
		assertEquals("First note.", result.get(0).getNote());
		verify(notesRepository, times(1)).findByPatId("1");
	}
	
	@Test
	void testAddNote() {
		Notes note = new Notes("1","1","TestPatient", "New medical note.");
		when(notesRepository.save(note)).thenReturn(note);
		
		Notes result = notesService.addNote(note);
		assertEquals("New medical note.", result.getNote());
		verify(notesRepository, times(1)).save(note);
	}
	
	@Test
	void testUpdateNote() {
		Notes existingNote = new Notes("1","1","TestPatient", "Old note.");
		Notes updatedNote = new Notes("1","1","TestPatient", "Updated note.");
		
		when(notesRepository.findById("1")).thenReturn(Optional.of(existingNote));
		when(notesRepository.save(updatedNote)).thenReturn(updatedNote);
		
		Notes result = notesService.updateNote("1", updatedNote);
		assertEquals("Updated note.", result.getNote());
		verify(notesRepository, times(1)).save(updatedNote);
	}
	
	@Test
	void testUpdateNote_NotFound() {
		Notes updatedNote = new Notes("1","1","TestPatient", "Updated note.");
		when(notesRepository.findById("1")).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> notesService.updateNote("1", updatedNote));
	}
	
	@Test
	void testDeleteNote() {
		Notes existingNote = new Notes("1","1","TestPatient", "Medical note to delete.");
		
		when(notesRepository.findById("1")).thenReturn(Optional.of(existingNote));
		
		doNothing().when(notesRepository).deleteById("1");
		
		notesService.deleteNoteById("1");
		
		verify(notesRepository, times(1)).deleteById("1");
	}
	
	@Test
	void testDeleteNote_NotFound() {
		doThrow(EmptyResultDataAccessException.class).when(notesRepository).deleteById("1");
		
		assertThrows(RuntimeException.class, () -> notesService.deleteNoteById("1"));
	}
}
