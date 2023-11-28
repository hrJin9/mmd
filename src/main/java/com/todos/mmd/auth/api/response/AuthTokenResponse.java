package com.todos.mmd.auth.api.response;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class AuthTokenResponse {

    private final String accessToken;
    private final String grantType;

    public static AuthTokenResponse of(String accessToken, String grantType) {
        return new AuthTokenResponse(
                accessToken,
                grantType
        );
    }


}
