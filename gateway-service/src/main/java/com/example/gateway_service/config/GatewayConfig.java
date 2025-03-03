package com.example.gateway_service.config;
import com.example.gateway_service.filter.JwtAuthenticationFilter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://USER-SERVICE"))
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://USER-SERVICE"))
                .build();
    }
}
