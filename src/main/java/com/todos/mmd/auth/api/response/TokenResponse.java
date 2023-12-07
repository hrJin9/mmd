package com.todos.mmd.auth.api.response;

import lombok.*;

import java.time.Duration;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private long expiresIn;

    public static TokenResponse of(String accessToken, String refreshToken, String grantType, long expiresIn) {
        return new TokenResponse(
                accessToken,
                refreshToken,
                grantType,
                expiresIn
        );
    }
}
