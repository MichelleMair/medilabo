package com.medilabo.gateway.configurations;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Value("${security.jwt.secret}")
	private String jwtKey;
	
	@Bean
	public ReactiveJwtDecoder jwtDecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), MacAlgorithm.HS256.getName());
		return NimbusReactiveJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}
	
	@Bean
	public JwtEncoder jwtEncoder() {
		SecretKeySpec secretKey = new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8), MacAlgorithm.HS256.getName());
		JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(
				new com.nimbusds.jose.jwk.JWKSet(
					new com.nimbusds.jose.jwk.OctetSequenceKey.Builder(secretKey.getEncoded())
						.algorithm(JWSAlgorithm.HS256)
						.build()
				)
		);
		return new NimbusJwtEncoder(jwkSource);
	}
	
	public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
		return jwt -> {
			Collection<GrantedAuthority> authorities = jwt.getClaimAsStringList("roles").stream()
					.map(role -> "ROLE_" + role)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			return Mono.just(new JwtAuthenticationToken(jwt, authorities));
		};
	}
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchanges -> exchanges
				.pathMatchers("/api/auth/**").permitAll()
				.pathMatchers("/api/patients/**", "/api/notes/**").hasAnyRole("USER", "ADMIN")
				.anyExchange().authenticated())
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.addFilterAt(new LogJWTFilter(), SecurityWebFiltersOrder.AUTHENTICATION);
		
		return http.build();
	}
	
	@Bean
	public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails user = User.withUsername("user")
					.password(passwordEncoder.encode("password"))
					.roles("USER")
					.build();
		UserDetails admin = User.withUsername("admin")
				.password(passwordEncoder.encode("password"))
				.roles("ADMIN")
				.build();		
		
		return new MapReactiveUserDetailsService(user);
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
}
