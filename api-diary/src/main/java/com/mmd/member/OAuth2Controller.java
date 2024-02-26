package com.mmd.member;

import com.mmd.application.AuthService;
import com.mmd.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth2")
public class OAuth2Controller {
    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<?> OAuth2Login() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/register")
    public ResponseEntity<Void> OAuth2Register(@RequestHeader(HttpHeaders.AUTHORIZATION) String email) {
        authService.updateOAuth2MemberRole(email);
        return ResponseEntity.noContent().build();
    }


}
