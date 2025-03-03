package com.example.gateway_service.filter;
import com.example.gateway_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.function.Predicate;

//GatewayFilter -> Interface that allows us to implement custom filters in Spring cloud Gateway.
//GatewayFilterChain: Represents the sequence of filters in the request processing pipeline.
//HttpStatus: Represents HTTP response status codes.
//ServerHttpRequest: Represents an HTTP request in a reactive (non-blocking) environment.
//ServerHttpResponse: Represents an HTTP response in a reactive (non-blocking) environment.
//GatewayFilterChain chain: Represents the chain of filters that the request will pass through.
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Retrieve the current HTTP request from exchange
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
        // Creates a list of API endpoints that do not require authentication.
        final List<String> apiEndpoints = List.of("/api/v1/auth/login", "/api/v1/auth/register", "/eureka");
        //Predicate<ServerHttpRequest>: A function that takes ServerHttpRequest and returns true or false.
        // It checks if the request path is not in apiEndpoints (i.e., it is a secured endpoint).
        //stream().noneMatch(...): Iterates over apiEndpoints and checks if the request URI does not contain any of them.
        //If the request is not in the open endpoints list, then isApiSecured will return true.
        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));
        //If the request requires authentication (i.e., it is not in the open endpoints list), proceed to check authentication.
        if(isApiSecured.test(request)) {
            //Calls the authMissing method to check if the Authorization header is missing.
            //If missing, it calls onError(exchange), which sends a 401 UNAUTHORIZED response.
            if(authMissing(request)) return onError(exchange);
            //getOrEmpty("Authorization").get(0): Retrieves the first value of the Authorization header.
            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            //hecks if the token starts with Bearer
            if(token != null && token.startsWith("Bearer ")) token = token.substring(7);
            try {
                // validate token
                jwtUtil.validateToken(token);
            }catch (Exception e) {
                return onError(exchange);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private Boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
