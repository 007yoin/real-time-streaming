package com.live_stream.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private static final List<PathPatternRequestMatcher> WHITELIST = List.of(
            PathPatternRequestMatcher.withDefaults().matcher("/auth/login"),
            PathPatternRequestMatcher.withDefaults().matcher("/auth/logout"),
            PathPatternRequestMatcher.withDefaults().matcher(POST, "/user"),
            PathPatternRequestMatcher.withDefaults().matcher(GET, "/auth/check"),
            PathPatternRequestMatcher.withDefaults().matcher(POST, "/auth/refresh")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isWhitelisted(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearer = request.getHeader("Authorization");

        JwtStatus status;

        if (bearer == null || !bearer.startsWith("Bearer ")) {
            status = JwtStatus.MISSING;
        } else {
            String token = bearer.substring(7);
            status = jwtUtil.validateTokenStatus(token);
        }

        log.info("doFilterInternal tkn status: {}", status);

        switch (status) {
            case VALID -> filterChain.doFilter(request, response);
            case EXPIRED, SIGNATURE_INVALID, MISSING -> sendError(response, SC_UNAUTHORIZED, status.getMessage());
            case MALFORMED, ILLEGAL -> sendError(response, SC_BAD_REQUEST, status.getMessage());
            case UNKNOWN -> sendError(response, SC_INTERNAL_SERVER_ERROR, status.getMessage());
        }
    }

    private boolean isWhitelisted(HttpServletRequest request) {
        return WHITELIST.stream().anyMatch(matcher -> matcher.matches(request));
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

}