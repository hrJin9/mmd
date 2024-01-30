package com.mmd.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionAdvice {
    
    /* 공통 예외 */
    @ExceptionHandler(MmdApiException.class)
    public ResponseEntity<ExceptionResponse> handleMmdApiException(MmdApiException e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ExceptionResponse> handlerBadRequestExcception(HttpClientErrorException.BadRequest e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }
}
