package com.mmd.member.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthRequest {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @ApiModel(description = "로그인 요청 Parameter")
    public static class LoginRequest {
        @NotBlank
        @Email
        @ApiModelProperty(value = "이메일", required = true, example = "dallae@gmail.com")
        private String email;

        @NotBlank
        @ApiModelProperty(value = "비밀번호", required = true, example = "dallae123!@#")
        private String password;
    }
}
