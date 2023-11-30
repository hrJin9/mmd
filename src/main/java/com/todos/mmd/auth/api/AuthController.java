package com.todos.mmd.auth.api;

import com.todos.mmd.auth.api.response.AuthTokenResponse;
import com.todos.mmd.auth.application.AuthService;
import com.todos.mmd.auth.api.request.AuthRequest;
import com.todos.mmd.auth.application.dto.AdminCreateDto;
import com.todos.mmd.auth.application.dto.LoginDto;
import com.todos.mmd.auth.application.dto.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/token")
public class AuthController {
    private final AuthService authService;

    /* 일반회원 회원가입 */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid AuthRequest.MemberCreateRequest request){
        authService.register(MemberCreateDto.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    /* 관리자 회원가입 */
    @PostMapping("/register/admin")
    public ResponseEntity<Void> registerAmdin(@RequestBody @Valid AuthRequest.AdminCreateRequest request) {
        authService.registerAdmin(AdminCreateDto.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@RequestBody @Valid AuthRequest.LoginRequest request){
        AuthTokenResponse token = authService.login(LoginDto.from(request));
        return ResponseEntity.ok(token);
    }

}
