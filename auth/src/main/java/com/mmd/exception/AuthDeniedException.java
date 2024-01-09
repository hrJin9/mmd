package com.mmd.exception;

/* Jwt관련 exception 처리 */
public class AuthDeniedException extends AuthException {

    public AuthDeniedException(String message) {
        super(message);
    }

    public AuthDeniedException(Throwable cause) {
        super(cause);
    }

    public AuthDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

}
