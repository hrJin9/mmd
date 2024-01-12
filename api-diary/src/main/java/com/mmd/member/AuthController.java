package com.mmd.member;

import com.mmd.application.AuthService;
import com.mmd.application.dto.TokenDto;
import com.mmd.member.mapper.ServiceDtoMapper;
import com.mmd.member.request.AuthRequest;
import com.mmd.member.response.TokenResponse;
import com.mmd.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthRequest.LoginRequest request){
        TokenDto tokenDto = authService.login(ServiceDtoMapper.mapping(request));
        return ResponseEntity.ok()
                .body(TokenResponse.from(tokenDto));
    }

    /* access 토큰 재발급 */
    @GetMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@AuthenticationPrincipal MemberDetails memberDetails) {
        TokenDto tokenDto = authService.reissueAccessToken(memberDetails);
        return ResponseEntity.ok()
                .body(TokenResponse.from(tokenDto));
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
