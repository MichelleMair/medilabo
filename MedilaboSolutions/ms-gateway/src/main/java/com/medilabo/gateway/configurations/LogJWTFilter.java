package com.medilabo.gateway.configurations;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public class LogJWTFilter implements WebFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		System.out.println("HEADER JWT : " + authHeader);
		return chain.filter(exchange);
	}

}
