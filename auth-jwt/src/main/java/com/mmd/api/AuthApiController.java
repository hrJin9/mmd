package com.mmd.api;

import com.mmd.api.request.AuthRequest;
import com.mmd.api.response.TokenResponse;
import com.mmd.application.AuthService;
import com.mmd.application.dto.LoginDto;
import com.mmd.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    private final AuthService authService;

    /* 일반회원 회원가입 */
    @PostMapping("/member/register")
    public ResponseEntity<Void> register(@RequestBody @Valid AuthRequest.MemberCreateRequest request){
        authService.register(MemberCreateDto.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthRequest.LoginRequest request){
        TokenResponse tokenResponse = authService.login(LoginDto.from(request));
        return ResponseEntity.ok()
                .body(tokenResponse);
    }

    /* access 토큰 재발급 */
    @GetMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@AuthenticationPrincipal MemberDetails memberDetails) {
        TokenResponse tokenResponse = authService.reissueAccessToken(memberDetails);
        return ResponseEntity.ok()
                .body(tokenResponse);
    }

    /* 로그아웃 */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            Principal principal,
            @RequestHeader("refreshToken") String refreshToken
    ) {
        authService.logout(principal.getName(), refreshToken);
        return ResponseEntity.noContent().build();
    }
    

}
