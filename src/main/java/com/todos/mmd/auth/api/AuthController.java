package com.todos.mmd.auth.api;

import com.todos.mmd.auth.api.response.TokenResponse;
import com.todos.mmd.auth.application.AuthService;
import com.todos.mmd.auth.api.request.AuthRequest;
import com.todos.mmd.auth.application.MemberDetails;
import com.todos.mmd.auth.application.dto.AdminCreateDto;
import com.todos.mmd.auth.application.dto.LoginDto;
import com.todos.mmd.auth.application.dto.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    /* 일반회원 회원가입 */
    @PostMapping("/member/register")
    public ResponseEntity<Void> register(@RequestBody @Valid AuthRequest.MemberCreateRequest request){
        authService.register(MemberCreateDto.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 관리자 회원가입 */
    @PostMapping("/admin/register")
    public ResponseEntity<Void> registerAmdin(@RequestBody @Valid AuthRequest.AdminCreateRequest request) {
        authService.registerAdmin(AdminCreateDto.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthRequest.LoginRequest request){
        TokenResponse token = authService.login(LoginDto.from(request));
        return ResponseEntity.ok(token);
    }

    /* access 토큰 재발급 */
    @GetMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@AuthenticationPrincipal MemberDetails memberDetails) {
        TokenResponse token = authService.reissue(memberDetails);
        return ResponseEntity.ok(token);
    }

    /* 로그아웃 */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Principal principal, @RequestHeader("refreshToken") String refreshToken) {
        authService.logout(principal.getName(), refreshToken);
        return ResponseEntity.noContent().build();
    }

}
