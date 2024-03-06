package com.mmd.auth;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.application.OAuthService;
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
    public ResponseEntity<Void> oauthLogin(@PathVariable OAuthProvider oAuthProvider) {
        String redirectUri = oAuthService.findLoginRedirectUri(oAuthProvider);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }

    @GetMapping("/{oauthProvider}")
    public ResponseEntity<String> OAuth2Register(@PathVariable OAuthProvider oauthProvider,
                                                 @RequestParam String authorizationCode) {
//        authService.updateOAuth2MemberRole(email);
        return ResponseEntity.ok(oAuthService.login(oauthProvider, authorizationCode));
    }
}
