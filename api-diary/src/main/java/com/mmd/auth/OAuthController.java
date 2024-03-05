package com.mmd.auth;

import com.mmd.oauth.application.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth2")
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/login/{oAuthProvider}")
    public ResponseEntity<Void> oauthLogin(@PathVariable String oAuthProvider) {
        String redirectUri = oAuthService.findLoginRedirectUri(oAuthProvider);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }

    @GetMapping("/{oauthProvider}")
    public ResponseEntity<Void> OAuth2Register(@RequestParam String authorizationCode) {
//        authService.updateOAuth2MemberRole(email);
        return ResponseEntity.noContent().build();
    }
}
