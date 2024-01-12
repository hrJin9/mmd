package com.mmd.application.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private long expiresIn;

    public static TokenDto of(String accessToken, String refreshToken, String grantType, long expiresIn) {
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType(grantType)
                .expiresIn(expiresIn).build();
    }
}
