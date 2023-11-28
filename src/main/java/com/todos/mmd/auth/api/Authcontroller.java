package com.todos.mmd.auth.api;

import com.todos.mmd.auth.application.dto.TokenDto;
import com.todos.mmd.auth.application.MemberLoginService;
import com.todos.mmd.auth.api.request.MemberRequest;
import com.todos.mmd.auth.api.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Authcontroller {
    private final MemberLoginService memberLoginService;

    /* 회원가입 */
    @PostMapping("/register")
    public MemberResponse registerUser(@RequestBody @Valid MemberRequest.RegisterMember request, BindingResult bindingResult){

        // TODO : Request에서 registerDate등은 어떻게 처리할것인지? JSON에서 데이터를 다 받아와야하나?

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
        }
        return memberLoginService.registerUser(request.convertToServiceDto());
    }

    /* 로그인 */
    @PostMapping("/login")
    public TokenDto getLoginToken(@RequestBody @Valid MemberRequest.LoginMember request){
        return memberLoginService.login(request.convertToServiceDto());
    }



}
