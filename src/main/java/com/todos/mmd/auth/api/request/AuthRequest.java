package com.todos.mmd.auth.api.request;

import com.todos.mmd.auth.application.dto.JoinDto;
import com.todos.mmd.auth.application.dto.LoginDto;
import com.todos.mmd.auth.domain.UseStauts;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class AuthRequest {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class JoinMember {
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

        public JoinDto toServiceDto() {
            return new JoinDto(
                    this.email,
                    this.pwd,
                    this.name,
                    this.phone,
                    this.address,
                    this.registerDate,
                    this.lastLoginDate,
                    this.useStauts
            );
        }
    }

    @Getter
    public static class LoginMember {
        private String email;
        private String pwd;

        public LoginDto toServiceDto() {
            return new LoginDto(
                    this.email,
                    this.pwd
            );
        }
    }

}
