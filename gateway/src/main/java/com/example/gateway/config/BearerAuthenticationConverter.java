package com.example.gateway.config;

import com.example.gateway.model.AuthToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class BearerAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        try {
            return Mono.justOrEmpty(exchange).flatMap(this::getToken);
        } catch (AccessDeniedException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }
    }

    private Mono<Authentication> getToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(new AuthToken(getAuthHeader(exchange.getRequest())));
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
    }
}
