package com.mmd.member.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MemberRequest {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @ApiModel(description = "일반 회원 회원가입 요청 Parameter")
    public static class MemberCreateRequest {
        @NotBlank
        @Email
        @ApiModelProperty(value = "이메일", required = true, example = "dallae@gmail.com")
        private String email;

        @NotBlank
        @ApiModelProperty(value = "비밀번호", required = true, example = "dallae123!@#")
        private String password;

        @NotBlank
        @ApiModelProperty(value = "닉네임", required = true, example = "달래")
        private String nickName;

        @NotNull
        @ApiModelProperty(value = "이름", required = true, example = "진혜린")
        private String name;

        @NotBlank
        @ApiModelProperty(value = "핸드폰 번호", required = true, example = "010-1234-1234")
        private String phone;
        
        @ApiModelProperty(value = "주소", example = "인천시 계양구")
        private String address;
    }

    @Getter
    @NoArgsConstructor(access =  AccessLevel.PROTECTED)
    @AllArgsConstructor
    @ApiModel(description = "일반 회원 정보 수정 요청 Parameter")
    public static class MemberUpdateRequest {
        @ApiModelProperty(value = "닉네임", example = "달래랑 머루랑")
        private String nickName;

        @ApiModelProperty(value = "이름", example = "김혜린")
        private String name;
        
        @ApiModelProperty(value = "핸드폰 번호", example = "010-1234-0000")
        private String phone;

        @ApiModelProperty(value = "주소", example = "인천시 서구")
        private String address;
    }
}
