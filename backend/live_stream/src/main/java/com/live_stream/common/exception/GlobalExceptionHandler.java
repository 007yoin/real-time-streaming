package com.live_stream.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // DB 제약 조건 위반 처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // DB UNIQUE 제약조건 이름 (users.login_id에 설정된 제약)
        log.error(ex.getMessage(), ex);
        if (isUniqueViolationOn("users_login_id_key", ex)) {
            return new ErrorResponse(400, "이미 존재하는 아이디입니다.");
        }
        return new ErrorResponse(400, "잘못된 요청입니다. (데이터 무결성 위반)");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleBadCredentials(BadCredentialsException ex) {
        return new ErrorResponse(400, ex.getMessage());
    }

    // 모든 기타 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorResponse(500, "서버 내부 오류가 발생했습니다.");
    }

    // 유니크 제약 위반인지 확인하는 헬퍼 메서드
    private boolean isUniqueViolationOn(String constraintName, Throwable e) {
        Throwable cause = e;
        while (cause != null) {
            String message = cause.getMessage();
            if (message != null && message.contains(constraintName)) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}