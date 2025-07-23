package com.authservice.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtStatus {
    VALID(null),
    MISSING("토큰이 존재하지 않습니다."),
    EXPIRED("토큰이 만료되었습니다."),
    MALFORMED("토큰 형식이 올바르지 않습니다."),
    SIGNATURE_INVALID("서명이 위조되었습니다."),
    ILLEGAL("잘못된 요청입니다."),
    UNKNOWN("토큰 처리 중 오류가 발생했습니다.");

    private final String message;
}

