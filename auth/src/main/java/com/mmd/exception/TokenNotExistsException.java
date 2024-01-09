package com.mmd.exception;

/* Jwt관련 exception 처리 */
public class TokenNotExistsException extends AuthException {

    public TokenNotExistsException(String message) {
        super(message);
    }

    public TokenNotExistsException(Throwable cause) {
        super(cause);
    }

    public TokenNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
