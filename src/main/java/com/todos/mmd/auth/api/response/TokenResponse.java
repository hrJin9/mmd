package com.todos.mmd.auth.api.response;

import lombok.*;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String grantType;
    private final long expiresIn;

    public static TokenResponse of(String accessToken, String refreshToken, String grantType, long expiresIn) {
        return new TokenResponse(
                accessToken,
                refreshToken,
                grantType,
                expiresIn
        );
    }
}
