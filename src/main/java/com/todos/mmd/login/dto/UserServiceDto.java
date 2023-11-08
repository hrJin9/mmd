package com.todos.mmd.login.dto;

import com.todos.mmd.login.enums.UseStauts;
import com.todos.mmd.login.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserServiceDto {

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

        public User toUser(String pwd){
            return User.builder()
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
    }

}
