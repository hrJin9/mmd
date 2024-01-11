package com.mmd.exception;

/* Jwt관련 exception 처리 */
public class TokenNotFoundException extends AuthException {

    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(Throwable cause) {
        super(cause);
    }

    public TokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
