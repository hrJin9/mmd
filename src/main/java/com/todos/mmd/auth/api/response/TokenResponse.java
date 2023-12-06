package com.todos.mmd.auth.api.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static TokenResponse of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new TokenResponse(
                accessToken,
                refreshToken,
                grantType,
                expiresIn
        );
    }
}
