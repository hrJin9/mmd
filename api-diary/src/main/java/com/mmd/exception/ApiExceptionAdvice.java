package com.mmd.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionAdvice {
    
    /* 공통 예외 */
    @ExceptionHandler(MmdApiException.class)
    public ResponseEntity<ExceptionResponse> handleMmdApiException(MmdApiException e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }
}
