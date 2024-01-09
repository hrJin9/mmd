package com.mmd.api.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AuthRequest {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
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

        private String address;
    }
//
//    @Getter
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    @AllArgsConstructor
//    public static class AdminCreateRequest {
//
//        @NotNull
//        @Email
//        private String email;
//
//        @NotNull
//        private String password;
//
//        @NotBlank
//        private String name;
//
//        @NotBlank
//        private String phone;
//
//    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class LoginRequest {
        @NotNull
        @Email
        private String email;

        @NotNull
        private String password;
    }
}
