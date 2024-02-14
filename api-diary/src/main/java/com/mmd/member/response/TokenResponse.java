package com.mmd.member.response;

import com.mmd.application.dto.TokenDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ApiModel(value = "auth 토큰 response data")
public class TokenResponse {
    @ApiModelProperty(value = "억세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYWxsYWVAZ21haWwuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDc5OTE4MTV9.G-H5ahkG43y5AIuA-r73CMecBqJOt0kwlf9GVK00GPKvDiATOBJugZkElHqcGGz0H7yFBQubLzaOQwZHk4ShjA")
    private final String accessToken;
    @ApiModelProperty(value = "리프레시 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYWxsYWVAZ21haWwuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDc5OTE4MTV9.G-H5ahkG43y5AIuA-r73CMecBqJOt0kwlf9GVK00GPKvDiATOBJugZkElHqcGGz0H7yFBQubLzaOQwZHk4ShjA")
    private final String refreshToken;
    @ApiModelProperty(value = "토큰 타입", example = "Bearer")
    private final String grantType;
    @ApiModelProperty(value = "만료 시간", example = "108000")
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
