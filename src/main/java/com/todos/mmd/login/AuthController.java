package com.todos.mmd.login;

import com.todos.mmd.jwt.dto.TokenDto;
import com.todos.mmd.jwt.service.UserLoginService;
import com.todos.mmd.login.dto.UserRequest;
import com.todos.mmd.login.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
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
