package com.live_stream.common.auth;

import static com.live_stream.common.jwt.JwtStatus.VALID;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.OK;

import com.live_stream.common.jwt.JwtStatus;
import com.live_stream.common.jwt.JwtUtil;
import com.live_stream.common.security.CustomUserDetailsDto;
import com.live_stream.domain.user.dto.LoginRequestDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @ResponseStatus(OK)
    @PostMapping("/auth/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.debug("Login request");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getLoginId(),
                        loginRequestDto.getPassword()
                )
        );

        CustomUserDetailsDto principal = (CustomUserDetailsDto) authentication.getPrincipal();
        String loginId = principal.getUsername();
        String name = principal.getName();
        String role = principal.getRole().name();

        long accessExpire = 1000 * 60 * 15L;
        long refreshExpire = 1000 * 60 * 60 * 24 * 7L;

        String accessToken = jwtUtil.createJwt(loginId, role, name, accessExpire);
        String refreshToken = jwtUtil.createJwt(loginId, role, name, refreshExpire);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge((int) (refreshExpire / 1000));
        response.addCookie(refreshCookie);

        return accessToken;
    }

    @GetMapping("/auth/check")
    public void validateAccessToken(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Validate access token");

        String bearer = request.getHeader("Authorization");

        if (bearer == null || !bearer.startsWith("Bearer ")) {
            response.setStatus(SC_UNAUTHORIZED);
            return;
        }

        String token = bearer.substring(7);
        JwtStatus jwtStatus = jwtUtil.validateTokenStatus(token);

        if (jwtStatus != VALID) {
            response.setStatus(SC_UNAUTHORIZED);
        }
    }

    @PostMapping("/auth/refresh")
    public String refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshTokenFromCookies(request);

        if (refreshToken == null || jwtUtil.validateTokenStatus(refreshToken) != VALID) {
            response.setStatus(SC_BAD_REQUEST);
            log.debug("Refreshing access token fail");
            return null;
        }

        log.debug("Refreshing access token success");
        // Access Token 재발급 (15분)
        return jwtUtil.createJwt(
                jwtUtil.getLoginId(refreshToken),
                jwtUtil.getRole(refreshToken),
                jwtUtil.getName(refreshToken),
                1000 * 60 * 15L
        );
    }

    @ResponseStatus(OK)
    @PostMapping("/auth/logout")
    public void logout(HttpServletResponse response) {
        log.debug("Logout request");

        Cookie refreshCookie = new Cookie("refreshToken", "");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0); // 즉시 만료
        response.addCookie(refreshCookie);
    }

    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
