package com.todos.mmd.api.login.dto;

import com.todos.mmd.domain.login.dto.UserServiceDto;
import com.todos.mmd.domain.login.enums.UseStauts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class UserRequest {

    @Getter @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class RegisterUser {
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
        private String addr;

        @NotBlank
        private String registerDate;

        @NotBlank
        private String lastLoginDate;

        @NotNull
        private UseStauts useStauts;

        public UserServiceDto.RegisterUser convertToServiceDto(){
            return UserServiceDto.RegisterUser.builder()
                    .email(email)
                    .pwd(pwd)
                    .name(name)
                    .phone(phone)
                    .addr(addr)
                    .registerDate(registerDate)
                    .lastLoginDate(lastLoginDate)
                    .useStauts(useStauts).build();
        }

    }

    @Getter @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class LoginUser {
        private String email;
        private String pwd;

        public UserServiceDto.LoginUser convertToServiceDto() {
            return UserServiceDto.LoginUser.builder()
                    .email(email)
                    .pwd(pwd)
                    .build();
        }
    }


}
