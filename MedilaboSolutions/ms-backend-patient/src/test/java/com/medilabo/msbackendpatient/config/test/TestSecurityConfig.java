package com.medilabo.msbackendpatient.config.test;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.medilabo.msbackendpatient.config.SecurityConfig;

@Profile("test")
@TestConfiguration
@ImportAutoConfiguration(exclude = SecurityConfig.class)
public class TestSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.anyRequest().permitAll());
		return http.build();
	}
}
