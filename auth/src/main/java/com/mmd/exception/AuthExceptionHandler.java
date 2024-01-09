package com.mmd.exception;

import com.mmd.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AuthExceptionHandler {
    /* auth 로직 예외 */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(AuthException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
    }
}
