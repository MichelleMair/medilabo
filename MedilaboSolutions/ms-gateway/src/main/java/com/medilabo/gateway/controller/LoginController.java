package com.medilabo.gateway.controller;

import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Value("${auth.username}")
	private String username;
	
	@Value("${auth.password}")
	private String password;

	
	private final JwtEncoder jwtEncoder;
	private final PasswordEncoder passwordEncoder;
	
	public LoginController(JwtEncoder jwtEncoder, PasswordEncoder passwordEncoder) {
		this.jwtEncoder = jwtEncoder;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/api/auth")
	public Map<String, String> authenticate(@RequestBody Map<String, String> credentials) {
		logger.info("Requête POST reçue pour /api/auth avec les données de connexion: {}", credentials);
		
		String providedUsername = credentials.get("username");
		String providedPassword = credentials.get("password");
		
		logger.info("Username fourni: {}, Password fourni: {}", providedUsername, providedPassword);
		
		if (username.equals(providedUsername) && passwordEncoder.matches(providedPassword, password)) {
			logger.info("Authentification réussie pour: {} ", providedUsername);
			
			Instant now = Instant.now();
			JwtClaimsSet claims = JwtClaimsSet.builder()
					.issuer("MedilaboSolutions")
					.issuedAt(now)
					.expiresAt(now.plusSeconds(3600)) //1h
					.subject(providedUsername)
					.claim("roles", "USER")
					.build();
			
			String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
			logger.info("Token JWT généré: {} ", token);
			return Map.of("token", token);
		}
		logger.info("Echec de l'authentification pour l'utilisateur: {} ", providedUsername);
		throw new RuntimeException("Invalid username or password");
	}
	
}
