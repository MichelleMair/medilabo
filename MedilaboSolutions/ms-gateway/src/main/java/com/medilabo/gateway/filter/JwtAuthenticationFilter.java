package com.medilabo.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
	
	private final ReactiveJwtDecoder jwtDecoder; 
	
	public JwtAuthenticationFilter(ReactiveJwtDecoder jwtDecoder) {
		super(Config.class);
		this.jwtDecoder = jwtDecoder;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
			System.out.println("Authorization" + authHeader);
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				exchange.getResponse().getHeaders().add("Cache-Control",  "no-store, no-cache, must-revalidate, proxy-revalidate");
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}
			
			String token = authHeader.substring(7);
			System.out.println("Extracted Token : " + token);
			
			return this.jwtDecoder.decode(token)
					.flatMap(jwt -> {
						String subject = jwt.getSubject();
						exchange.getRequest().mutate()
								.header("X-Authenticated-user", subject)
								.build();
						exchange.getResponse().getHeaders().add("Cache-Control",  "no-store, no-cache, must-revalidate, proxy-revalidate");
						return chain.filter(exchange);
					})
					.onErrorResume(e -> {
						exchange.getResponse().getHeaders().add("Cache-Control",  "no-store, no-cache, must-revalidate, proxy-revalidate");
						exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
						return exchange.getResponse().setComplete();
					});
		};
	}

	public static class Config {
		//TODO: if needed, add config here
	}

}
