package com.example.gateway.filter;

import com.example.gateway.config.BearerAuthenticationConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class AuthorizationFilter extends AuthenticationWebFilter {

    private static final String BEARER = "Bearer ";
    private static final String X_USER_ID = "X-User-Id";
    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/api/auth/register", "/api/auth/login");

    public static List<String> getExcludedPaths() {
        return new ArrayList<>(EXCLUDED_PATHS);
    }

    @Value("${jwt.secret}")
    private String secretKey;

    public AuthorizationFilter(ReactiveAuthenticationManager authenticationManager,
                               BearerAuthenticationConverter bearerAuthenticationConverter) {
        super(authenticationManager);
        this.setServerAuthenticationConverter(bearerAuthenticationConverter);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (EXCLUDED_PATHS.contains(request.getPath().value())) {
            return chain.filter(exchange);
        }

        return proceedWithAuthentication(exchange, chain);
    }

    private Mono<Void> proceedWithAuthentication(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            String jwt = extractTokenFromHeader(exchange);
            validateToken(jwt);
            addUserIdToRequest(exchange, jwt, chain);
            return super.filter(exchange, chain);
        } catch (Exception e) {
            return handleError(exchange, "Authorization header is invalid");
        }
    }

    private String extractTokenFromHeader(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return authorizationHeader != null ? authorizationHeader.replace(BEARER, "") : null;
    }

    private void validateToken(String jwt) {
        Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
                .build()
                .parseClaimsJws(jwt);
    }

    private void addUserIdToRequest(ServerWebExchange exchange, String jwt, WebFilterChain chain) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        String userId = claims.getSubject();
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header(X_USER_ID, userId)
                .build();
        chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private Mono<Void> handleError(ServerWebExchange exchange, String error) {
        log.error("Authorization error: {}", error);
        byte[] bytes = error.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
