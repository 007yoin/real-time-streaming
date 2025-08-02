package com.rts.gatewayserver.route;

import com.rts.gatewayserver.jwt.JwtStatus;
import com.rts.gatewayserver.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@Slf4j
public class AuthCheckRouter {

    @Bean
    public RouterFunction<ServerResponse> authCheckRoute(JwtUtil jwtUtil) {
        return RouterFunctions
                // OPTIONS 요청 (CORS preflight)
                .route(RequestPredicates.OPTIONS("/access-router"),
                        request -> {
                            log.debug("[AuthCheckRouter] OPTIONS 요청 수신 - URI: {}", request.uri());
                            return ServerResponse.ok()
                                    .header("Access-Control-Allow-Origin", "http://localhost:3000")
                                    .header("Access-Control-Allow-Methods", "GET,OPTIONS")
                                    .header("Access-Control-Allow-Headers", "Authorization, Content-Type")
                                    .build();
                        })
                // GET 요청 (기존 인증 로직)
                .andRoute(RequestPredicates.GET("/access-router"),
                        request -> {
                            log.debug("[AuthCheckRouter] GET 요청 수신 - URI: {}", request.uri());

                            String bearer = request.headers().firstHeader(AUTHORIZATION);
                            log.debug("[AuthCheckRouter] Authorization 헤더: {}", bearer);

                            if (bearer == null || !bearer.startsWith("Bearer ")) {
                                log.warn("[AuthCheckRouter] 잘못된 Authorization 헤더: {}", bearer);
                                return ServerResponse.status(UNAUTHORIZED).build();
                            }

                            String token = bearer.substring(7);
                            log.debug("[AuthCheckRouter] 추출된 토큰: {}", token);

                            JwtStatus status = jwtUtil.validateTokenStatus(token);
                            log.debug("[AuthCheckRouter] 토큰 상태: {}", status);

                            if (status != JwtStatus.VALID) {
                                log.warn("[AuthCheckRouter] 토큰 검증 실패");
                                return ServerResponse.status(UNAUTHORIZED).build();
                            }

                            return ServerResponse.ok().build();
                        }
                );
    }
}
