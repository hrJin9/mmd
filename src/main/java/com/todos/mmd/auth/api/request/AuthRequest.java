package com.todos.mmd.auth.api.request;

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
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AdminCreateRequest {

        @NotNull
        @Email
        private String email;

        @NotNull
        private String password;

        @NotBlank
        private String name;

        @NotBlank
        private String phone;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginRequest {
        private String email;
        private String password;
    }

}
