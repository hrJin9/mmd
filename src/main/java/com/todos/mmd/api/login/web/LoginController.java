package com.todos.mmd.api.login.web;

import com.todos.mmd.jwt.security.dto.TokenDto;
import com.todos.mmd.domain.login.service.UserLoginService;
import com.todos.mmd.api.login.dto.UserRequest;
import com.todos.mmd.domain.login.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserLoginService userLoginService;

    /* 회원가입 */
    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody @Valid UserRequest.RegisterUser userRequest){
        return userLoginService.registerUser(userRequest.convertToServiceDto());
    }

    /* 로그인 */
    @PostMapping("/login")
    public TokenDto getLoginToken(@RequestBody UserRequest.LoginUser loginUser){
        return userLoginService.login(loginUser.convertToServiceDto());
    }



}
