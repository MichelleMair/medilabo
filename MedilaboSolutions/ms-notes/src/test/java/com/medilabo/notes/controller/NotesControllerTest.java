package com.medilabo.notes.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.notes.TestConfiguration.TestSecurityConfig;
import com.medilabo.notes.model.Notes;
import com.medilabo.notes.service.NotesService;

@WebMvcTest(NotesController.class)
@Import (TestSecurityConfig.class)
@ActiveProfiles("test")
public class NotesControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NotesService notesService;
	
	private Notes note;
	private static final String TEST_TOKEN = "Bearer test-token"; //Simule un JWT
	
	@BeforeEach
	void setUp() {
		note = new Notes("1",1,"TestPatient", "Medical note.");
	}
	
	@Test
	void testGetNotesByPatientId() throws Exception {
		when(notesService.getNotesByPatientId(1)).thenReturn(Arrays.asList(note));
		
		mockMvc.perform(get("/api/notes/1")
				.header("Authorization", TEST_TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].note").value("Medical note."));
	}
	
	@Test
	void testAddNote() throws Exception {
		when(notesService.addNote(Mockito.any(Notes.class))).thenReturn(note);
		
		mockMvc.perform(post("/api/notes")
				.header("Authorization", TEST_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(note)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.note").value("Medical note."));		
	}
	
	@Test
	void testUpdateNote() throws Exception {
		when(notesService.updateNote(eq("1"), Mockito.any(Notes.class))).thenReturn(note);
		
		mockMvc.perform(put("/api/notes/1")
					.header("Authorization", TEST_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(note)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.note").value("Medical note."));
	}
	
	@Test
	void testDeleteNote() throws Exception {
		doNothing().when(notesService).deleteNoteById("1");
		
		mockMvc.perform(delete("/api/notes/1")
				.header("Authorization", TEST_TOKEN))
			.andExpect(status().isNoContent());
	}
	
}
