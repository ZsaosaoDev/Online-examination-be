package com.meeting.springboot_meet.common.exception;

import com.meeting.springboot_meet.auth.domain.exception.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "code", ex.getErrorCode(),
                        "message", ex.getMessage()
                )
        );
    }
}

