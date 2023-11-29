package com.todos.mmd.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MmdException.class)
    public ResponseEntity<ExceptionResponse> handleMmdException(MmdException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionResponse> handleAuthException(AuthException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponse(exception.getMessage()));
    }
}
