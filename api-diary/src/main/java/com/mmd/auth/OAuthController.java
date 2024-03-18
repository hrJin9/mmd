package com.mmd.auth;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.application.OAuthService;
import com.mmd.oauth.client.response.OAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth2")
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/login/{oAuthProvider}")
    public ResponseEntity<String> oauthLogin(@PathVariable OAuthProvider oAuthProvider) {
        String redirectUri = oAuthService.findLoginRedirectUri(oAuthProvider);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }

    @GetMapping("/{oauthProvider}")
    public ResponseEntity<OAuthResponse> OAuth2Register(@PathVariable OAuthProvider oauthProvider,
                                                        @RequestParam(name = "code") String authorizationCode) {
        return ResponseEntity.ok(oAuthService.login(oauthProvider, authorizationCode));
    }
}
