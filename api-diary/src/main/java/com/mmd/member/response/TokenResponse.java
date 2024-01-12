package com.mmd.member.response;

import com.mmd.application.dto.TokenDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String grantType;
    private final long expiresIn;

    public static TokenResponse from(TokenDto tokenDto) {
        return new TokenResponse(
                tokenDto.getAccessToken(),
                tokenDto.getRefreshToken(),
                tokenDto.getGrantType(),
                tokenDto.getExpiresIn()
        );
    }
}
