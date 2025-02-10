package com.medilabo.gateway.configurations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.medilabo.gateway.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// Route for auth service
				.route("auth-service", r -> r.path("/api/auth")
						.filters(f -> f.stripPrefix(1))
						.uri("http://ms-gateway:8082"))
				//Route for patient service 
				.route("patient-service", r -> r.path("/api/patients/**")
						.filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
						.uri("http://ms-backend-patient:8080"))
				//Route for notes service
				.route("notes-service", r -> r.path("/api/notes/**")
						.filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
						.uri("http://ms-notes:8083"))
				//Route for risk service
				.route("diabetes-risk-service", r -> r.path("/api/risk/**")
						.filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
						.uri("http://ms-diabetes-risk:8084"))	
				//Default route
				.route("default-route", r -> r.path("/**")
						.filters(f -> f.setStatus(HttpStatus.NOT_FOUND))
						.uri("no://op"))
				// Route for frontend
				.route("frontend-service", r -> r.path("/ms-frontend/**")
						.filters(f -> f.rewritePath("/ms-frontend/(?<remaining>.*)", "/${remaining}"))
						.uri("http://ms-frontend:4200"))
				.build();
				
	}
}
