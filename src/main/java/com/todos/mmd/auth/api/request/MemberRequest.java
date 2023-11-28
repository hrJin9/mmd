package com.todos.mmd.auth.api.request;

import com.todos.mmd.auth.application.dto.MemberLoginDto;
import com.todos.mmd.auth.domain.UseStauts;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class MemberRequest {

    @Getter
    public static class RegisterMember {
        @NotNull
        @Email
        private String email;

        @NotNull
        private String pwd;

        @NotBlank
        private String name;

        @NotBlank
        private String phone;

        @NotBlank
        private String address;

        @NotBlank
        private String registerDate;

        @NotBlank
        private String lastLoginDate;

        @NotNull
        private UseStauts useStauts;

        public MemberLoginDto.RegisterMember convertToServiceDto(){
            return MemberLoginDto.RegisterMember.builder()
                    .email(email)
                    .pwd(pwd)
                    .name(name)
                    .phone(phone)
                    .address(address)
                    .registerDate(registerDate)
                    .lastLoginDate(lastLoginDate)
                    .useStauts(useStauts).build();
        }

    }

    @Getter
    public static class LoginMember {
        private String email;
        private String pwd;

        public MemberLoginDto.LoginMember convertToServiceDto() {
            return MemberLoginDto.LoginMember.builder()
                    .email(email)
                    .pwd(pwd)
                    .build();
        }
    }


}
