package com.medilabo.notes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.notes.model.Notes;
import com.medilabo.notes.service.NotesService;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

	private final NotesService notesService;
	
	// Constructor
	public NotesController(NotesService notesService) {
		this.notesService = notesService;
	}
	
	
	/**
	 * Endpoint to get all notes existing in database
	 * @return all notes
	 */
	@GetMapping
	public ResponseEntity<List<Notes>> getAllNotes() {
		List<Notes> notesList = notesService.getAllNotes();
		return ResponseEntity.ok(notesList);
	}
	
	/**
	 * Endpoint to get all notes for a specific patient
	 * @param patientId
	 * @return List of notes
	 */
	@GetMapping("/{patId}")
	public ResponseEntity<List<Notes>> getNotesByPatientId(@PathVariable int patId) {
		List<Notes> notesList = notesService.getNotesByPatientId(patId);
		return ResponseEntity.ok(notesList);
	}
	
	/**
	 * Endpoint to add a new note
	 * @param note the note to be added
	 * @return the added note
	 */
	@PostMapping
	public ResponseEntity<Notes> addNote (@RequestBody Notes note) {
		Notes savedNote = notesService.addNote(note);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
	}
	
	/**
	 * Endpoint to update an existing note
	 * @param noteId
	 * @param updatedNote
	 * @return updatedNote
	 */
	@PutMapping("/{noteId}")
	public ResponseEntity<Notes> updateNote(@PathVariable String noteId, @RequestBody Notes updatedNote) {
		Notes note = notesService.updateNote(noteId, updatedNote);
		return ResponseEntity.ok(note);
	}
	
	/**
	 * Deletes a note by its ID
	 * @param noteId
	 * @return ResponseEntity with no content
	 */
	@DeleteMapping("/{noteId}")
	public ResponseEntity<Void> deleteNote (@PathVariable String noteId) {
		notesService.deleteNoteById(noteId);
		return ResponseEntity.noContent().build();
	}
}
