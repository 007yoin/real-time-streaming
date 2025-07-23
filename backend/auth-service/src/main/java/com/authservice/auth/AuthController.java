package com.authservice.auth;

import com.authservice.jwt.JwtUtil;
import com.authservice.user.User;
import com.authservice.user.UserSearchService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static com.authservice.jwt.JwtStatus.VALID;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserSearchService uss;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/auth/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.debug("Login request");

        // 유저 조회
        User findUser = uss.findByLoginId(loginRequestDto.getLoginId());

        // 비밀번호 검증
        if (!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), findUser.getPassword())) {
            log.debug("Invalid password for user: {}", loginRequestDto.getLoginId());
            return null;
        }

        // 토큰 생성
        String loginId = findUser.getLoginId();
        String name = findUser.getName();
        String role = findUser.getRole().name();

        long accessExpire = 1000 * 60 * 15L;
        long refreshExpire = 1000 * 60 * 60 * 24 * 7L;

        String accessToken = jwtUtil.createJwt(loginId, role, name, accessExpire);
        String refreshToken = jwtUtil.createJwt(loginId, role, name, refreshExpire);

        // 리프레시 토큰을 쿠키에 저장
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge((int) (refreshExpire / 1000));
        response.addCookie(refreshCookie);

        return accessToken;
    }


    //TODO 제거 예정. (게이트웨이 라우터에서 검증)
//    @GetMapping("/auth/check")
//    public void validateAccessToken(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("auth check request");
//        log.debug("Validate access token");
//
//        String bearer = request.getHeader("Authorization");
//
//        if (bearer == null || !bearer.startsWith("Bearer ")) {
//            response.setStatus(SC_UNAUTHORIZED);
//            return;
//        }
//
//        String token = bearer.substring(7);
//        JwtStatus jwtStatus = jwtUtil.validateTokenStatus(token);
//
//        if (jwtStatus != VALID) {
//            response.setStatus(SC_UNAUTHORIZED);
//        }
//    }

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

