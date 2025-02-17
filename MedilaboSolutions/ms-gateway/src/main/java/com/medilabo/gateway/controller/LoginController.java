package com.medilabo.gateway.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
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
	
	/**
	 * Authenticates the user and generates a JWT token
	 * @param credentials The user's credentials (username and password)
	 * @return Map containing the JWT token and user role
	 */
	@PostMapping("/api/auth")
	public Map<String, String> authenticate(@RequestBody Map<String, String> credentials) {
		
		String providedUsername = credentials.get("username");
		String providedPassword = credentials.get("password");
		
		
		if (username.equals(providedUsername) && passwordEncoder.matches(providedPassword, password)) {
			logger.info("Authentification réussie pour: {} ", providedUsername);

			Instant now = Instant.now();
			
			String role = "user".equals(providedUsername) ? "USER" : "ADMIN";
			
			JwtClaimsSet claims = JwtClaimsSet.builder()
					.issuer("MedilaboSolutions")
					.issuedAt(now)
					.expiresAt(now.plusSeconds(3600)) //1h
					.subject(providedUsername)
					.claim("roles", List.of(role))
					.build();
			
			JwtEncoderParameters parameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
			
			String token = this.jwtEncoder.encode(parameters).getTokenValue();
			
			return Map.of(
					"token", token,
					"role", role,
					"username", providedUsername);
		}
		logger.info("Echec de l'authentification pour l'utilisateur: {} ", providedUsername);
		throw new RuntimeException("Invalid username or password");
	}
	
}
