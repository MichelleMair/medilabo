package com.medilabo.MedilaboSolutions.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	@Value("${auth.username}")
	private String username;
	
	@Value("${auth.password}")
	private String password;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
		String providedUsername = credentials.get("username");
		String providedPassword = credentials.get("password");
		
		if (username.equals(providedUsername) && "password".equals(providedPassword)) {
			return ResponseEntity.ok("Login successful");
		} else {
			return ResponseEntity.status(401).body("Invalid username or password");
		}
	}
}
