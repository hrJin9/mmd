package com.todos.mmd.auth.api;

import com.todos.mmd.auth.application.dto.TokenDto;
import com.todos.mmd.auth.application.AuthService;
import com.todos.mmd.auth.api.request.AuthRequest;
import com.todos.mmd.auth.api.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /* 회원가입 */
    @PostMapping("/join")
    public ResponseEntity<AuthResponse> joinUser(@RequestBody @Valid AuthRequest.JoinMember request, BindingResult bindingResult){
        AuthResponse response = authService.join(request.toServiceDto());
        return ResponseEntity.ok().body(response);
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> getLoginToken(@RequestBody @Valid AuthRequest.LoginMember request){
        return authService.login(request.convertToServiceDto());
    }



}
