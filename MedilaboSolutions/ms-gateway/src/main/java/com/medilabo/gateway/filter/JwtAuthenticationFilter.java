package com.medilabo.gateway.filter;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.medilabo.gateway.util.JwtConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.core.HttpHeaders;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
	
	private final String secret = JwtConstants.SECRET_KEY; 
	
	public JwtAuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String path = exchange.getRequest().getPath().toString();
			if (path.startsWith("/api/auth")) {
				return chain.filter(exchange);
			}
			
			String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}
			
			String token = authHeader.substring(7);
			
			try {
				//Validate the JWT token
				Key signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
				Claims claims= Jwts.parserBuilder()
						.setSigningKey(signingKey)
						.build()
						.parseClaimsJws(token)
						.getBody();
				
				//Add authenticated username to the headers
				String username = claims.getSubject();
				exchange.getRequest().mutate()
						.header("X-Authenticated-User", username)
						.build();
				
				//Allow the request to proceed
				return chain.filter(exchange);
				
			} catch (Exception e) {
				// Reject the request if JWT validation fails
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}
		};
	}

	public static class Config {
		//TODO: if needed, add config here
	}

}
