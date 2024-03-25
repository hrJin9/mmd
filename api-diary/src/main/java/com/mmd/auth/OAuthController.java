package com.mmd.auth;

import com.mmd.application.dto.TokenDto;
import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.application.OAuthService;
import com.mmd.oauth.client.response.OAuthUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Api(tags = "OAuth 인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth2")
public class OAuthController {
    private final OAuthService oAuthService;

    @ApiOperation(value = "OAuth 로그인 요청", notes = "OAuth 로그인 페이지로 리다이렉트 시킵니다.", tags = "OAuth 인증 API")
    @GetMapping("/login/{oAuthProvider}")
    public ResponseEntity<String> oauthLogin(@PathVariable OAuthProvider oAuthProvider) {
        String redirectUri = oAuthService.findLoginRedirectUri(oAuthProvider);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }

    @ApiOperation(value = "로그인 인증 후 처리", notes = "Authorization Code를 받아 회원가입 및 토큰을 발급합니다.", tags = "OAuth 인증 API")
    @GetMapping("/{oauthProvider}/authorize")
    public ResponseEntity<TokenDto> OAuth2Register(@PathVariable OAuthProvider oauthProvider,
                                                   @RequestParam(name = "code") String authorizationCode) {
        return ResponseEntity.ok(oAuthService.login(oauthProvider, authorizationCode));
    }
}
