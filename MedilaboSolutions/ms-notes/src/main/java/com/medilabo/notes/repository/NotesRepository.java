package com.medilabo.notes.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.notes.model.Notes;

@Repository
public interface NotesRepository extends MongoRepository<Notes, String> {

	/**
	 * Find all notes by patient id
	 * @param patientId
	 * @return list of notes
	 */
	List<Notes> findByPatId(int patId);
}
