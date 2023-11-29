package com.todos.mmd.auth.api.request;

import com.todos.mmd.auth.application.dto.MemberCreateDto;
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
    public static class MemberCreateRequest {
        @NotNull
        @Email
        private String email;

        @NotNull
        private String password;

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

        public MemberCreateDto toServiceDto() {
            return new MemberCreateDto(
                    this.email,
                    this.password,
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
    public static class LoginRequest {
        private String email;
        private String password;

        public LoginDto toServiceDto() {
            return new LoginDto(
                    this.email,
                    this.password
            );
        }
    }

}
