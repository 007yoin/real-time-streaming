package com.rts.gatewayserver.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.rts.gatewayserver.jwt.JwtStatus;
import com.rts.gatewayserver.jwt.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalJwtFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    private static final List<WhitelistedRoute> WHITELIST = List.of(
            new WhitelistedRoute(HttpMethod.POST, "/auth/login"),
            new WhitelistedRoute(HttpMethod.POST, "/auth/logout"),
            new WhitelistedRoute(HttpMethod.POST, "/user"),
            new WhitelistedRoute(HttpMethod.GET, "/auth/check"),
            new WhitelistedRoute(HttpMethod.POST, "/auth/refresh")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        String path = request.getURI().getPath();

        if (isWhitelisted(method, path)) {
            return chain.filter(exchange);
        }

        String bearer = request.getHeaders().getFirst(AUTHORIZATION);
        if (!hasValidBearer(bearer)) {
            log.debug("Missing or invalid Authorization header");
            return unauthorized(exchange);
        }

        String token = bearer.substring(7);
        JwtStatus status = jwtUtil.validateTokenStatus(token);
        if (status != JwtStatus.VALID) {
            log.debug("JWT validation failed: {}", status);
            return unauthorized(exchange);
        }

        String loginId = jwtUtil.getLoginId(token);
        String role = jwtUtil.getRole(token);
        String name = jwtUtil.getName(token);

        ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-User-Id", loginId)
                .header("X-User-Role", role)
                .header("X-User-Name", name)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isWhitelisted(HttpMethod method, String path) {
        return WHITELIST.stream().anyMatch(route -> route.matches(method, path));
    }

    private boolean hasValidBearer(String bearer) {
        return bearer != null && bearer.startsWith("Bearer ");
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private record WhitelistedRoute(HttpMethod method, String path) {
        boolean matches(HttpMethod reqMethod, String reqPath) {
            return this.method == reqMethod && reqPath.startsWith(this.path);
        }
    }
}
