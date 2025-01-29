package com.medilabo.notes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medilabo.notes.model.Notes;
import com.medilabo.notes.repository.NotesRepository;

@Service
public class NotesService {
	
	private final NotesRepository notesRepository;
	
	public NotesService(NotesRepository notesRepository) {
		this.notesRepository = notesRepository;
	}
	
	//Get all notes
	public List<Notes> getAllNotes() {
		return notesRepository.findAll();
	}

	// Get notes with patientID
	public List<Notes> getNotesByPatientId(int patId) {
		return notesRepository.findByPatId(patId);
	}
	
	//Add a new note
	public Notes addNote(Notes note) {
		return notesRepository.save(note);
	}
	
	//Update an existing note
	public Notes updateNote(String noteId, Notes updatedNote) {
		Optional<Notes> existingNote = notesRepository.findById(noteId);
		
		if (existingNote.isPresent()) {
			Notes note = existingNote.get();
			note.setPatient(updatedNote.getPatient());
			note.setNote(updatedNote.getNote());
			return notesRepository.save(note);
		} else {
			throw new RuntimeException("Note with ID " + noteId + " not found.");
		}
	}
	
	//Delete note
	public void deleteNoteById(String noteId) {
		Optional<Notes> existingNote = notesRepository.findById(noteId);
		
		if (existingNote.isPresent()) {
			notesRepository.deleteById(noteId);
		} else {
			throw new RuntimeException("Note with ID " + noteId + " not found.");
		}
	}
}
