package com.rts.gatewayserver.route;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.rts.gatewayserver.jwt.JwtStatus;
import com.rts.gatewayserver.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AuthCheckRouter {

    @Bean
    public RouterFunction<ServerResponse> authCheckRoute(JwtUtil jwtUtil) {
        return RouterFunctions
                .route(RequestPredicates.GET("/auth/check"),
                        request -> {
                            String bearer = request.headers().firstHeader(AUTHORIZATION);
                            if (bearer == null || !bearer.startsWith("Bearer ")) {
                                return ServerResponse.status(UNAUTHORIZED).build();
                            }

                            String token = bearer.substring(7);
                            JwtStatus status = jwtUtil.validateTokenStatus(token);
                            if (status != JwtStatus.VALID) {
                                return ServerResponse.status(UNAUTHORIZED).build();
                            }

                            return ServerResponse.ok().build();
                        }
                );
    }
}