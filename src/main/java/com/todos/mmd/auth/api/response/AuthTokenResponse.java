package com.todos.mmd.auth.api.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class AuthTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static AuthTokenResponse of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthTokenResponse(
                accessToken,
                refreshToken,
                grantType,
                expiresIn
        );
    }
}
