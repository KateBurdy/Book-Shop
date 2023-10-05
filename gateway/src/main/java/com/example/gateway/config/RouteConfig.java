package com.example.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;

import java.time.Duration;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RouteConfig {
    @Value("${gateway.response.timeout}")
    private int gatewayResponseTimeout;

    @Value("${gateway.connect.timeout}")
    private int gatewayConnectionTimeout;

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(5)
                .waitDurationInOpenState(Duration.ofMillis(gatewayResponseTimeout))
                .slidingWindowSize(2)
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(gatewayResponseTimeout))
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        log.info("Init Routes in gateway.");
        return builder.routes()
                        .route("auth-service",r -> r.path("/api/auth/**")
                                .filters(f -> f.circuitBreaker(config -> config
                                                .setName("auth")
                                                .setFallbackUri("forward:/fallback"))
                                        .metadata(RESPONSE_TIMEOUT_ATTR, gatewayResponseTimeout)
                                        .metadata(CONNECT_TIMEOUT_ATTR, gatewayConnectionTimeout))
                                .uri("http://localhost:8009"))
                        .route("file-service", r -> r.path("/api/files/**")
                                .filters(f -> f.circuitBreaker(config -> config
                                                .setName("file")
                                                .setFallbackUri("forward:/fallback"))
                                        .metadata(RESPONSE_TIMEOUT_ATTR, gatewayResponseTimeout)
                                        .metadata(CONNECT_TIMEOUT_ATTR, gatewayConnectionTimeout))
                                .uri("http://localhost:8007"))
                        .route("payment-service", r -> r.path("/api/users/{user_id}/orders/{order_id}/payments/**")
                                .filters(f -> f.circuitBreaker(config -> config
                                                .setName("payment")
                                                .setFallbackUri("forward:/fallback"))
                                        .metadata(RESPONSE_TIMEOUT_ATTR, gatewayResponseTimeout)
                                        .metadata(CONNECT_TIMEOUT_ATTR, gatewayConnectionTimeout))
                                .uri("http://localhost:8005/payments"))
                        .route("order-service", r -> r.path("/api/users/{user_id}/orders/**")
                                .filters(f -> f.circuitBreaker(config -> config
                                                .setName("order")
                                                .setFallbackUri("forward:/fallback"))
                                        .metadata(RESPONSE_TIMEOUT_ATTR, gatewayResponseTimeout)
                                        .metadata(CONNECT_TIMEOUT_ATTR, gatewayConnectionTimeout))
                                .uri("http://localhost:8004"))
                        .route("product-service", r -> r.path("/api/products/**")
                                .filters(f -> f.circuitBreaker(config -> config
                                                .setName("product")
                                                .setFallbackUri("forward:/fallback"))
                                        .metadata(RESPONSE_TIMEOUT_ATTR, gatewayResponseTimeout)
                                        .metadata(CONNECT_TIMEOUT_ATTR, gatewayConnectionTimeout))
                                .uri("http://localhost:8003"))
                        .route("author-service", r -> r.path("/api/authors/**")
                                .filters(f -> f.circuitBreaker(config -> config
                                                .setName("author")
                                                .setFallbackUri("forward:/fallback"))
                                        .metadata(RESPONSE_TIMEOUT_ATTR, gatewayResponseTimeout)
                                        .metadata(CONNECT_TIMEOUT_ATTR, gatewayConnectionTimeout))
                                .uri("http://localhost:8011"))
                        .route("user-service", r -> r.path("/api/users/**")
                                .filters(f -> f.circuitBreaker(config -> config
                                                .setName("user")
                                                .setFallbackUri("forward:/fallback"))
                                        .metadata(RESPONSE_TIMEOUT_ATTR, gatewayResponseTimeout)
                                        .metadata(CONNECT_TIMEOUT_ATTR, gatewayConnectionTimeout))
                                .uri("http://localhost:8088"))
                        .build();
    }
}
