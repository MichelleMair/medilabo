package com.medilabo.diabetes.risk.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.diabetes.risk.model.Note;

@FeignClient(name= "ms-notes", url="http://ms-notes:8082")
public interface NotesClient {
	
	@GetMapping("/api/notes/{patId}")
	List<Note> getNotesByPatientId(@PathVariable("patId") int patId);

}
