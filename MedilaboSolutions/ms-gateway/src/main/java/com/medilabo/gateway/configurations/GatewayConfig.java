package com.medilabo.gateway.configurations;

import org.springframework.cloud.gateway.filter.factory.SetResponseHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// Route for auth service
				.route("auth-service", r -> r.path("/api/auth/**")
						.filters(f -> f.rewritePath("/api/auth/(?<remaining>.*)", "/${remaining}")
										.preserveHostHeader()
										.filter(new SetResponseHeaderGatewayFilterFactory().apply(config -> {
											config.setName("Set-Cookie");
											config.setValue("JSESSIONID");
										})))
						.uri("http://localhost:8080"))
				//Route for patient service 
				.route("patient-service", r -> r.path("/api/patients/**")
						.filters(f -> f.rewritePath("/api/patients/(?<remaining>.*)", "/${remaining}")
										.preserveHostHeader()
										.addRequestHeader("Authorization", "Basic dXNlcjwYXNzd29yZA=="))
						.uri("http://localhost:8080"))
				// Route for frontend
				.route("frontend-service", r -> r.path("/ms-frontend/**")
						.filters(f -> f.rewritePath("/ms-frontend/(?<remaining>.*)", "/${remaining}"))
						.uri("http://localhost:4200"))
				.build();
				
	}
}
