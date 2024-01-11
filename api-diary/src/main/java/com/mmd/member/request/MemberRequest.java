package com.mmd.member.request;

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
    public static class MemberCreateRequest {
        @NotBlank
        private String memberId;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

        @NotNull
        private String name;

        @NotBlank
        private String phone;

        private String address;
    }

    @Getter
    @NoArgsConstructor(access =  AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MemberUpdateRequest {
        @NotBlank
        private String name;
        @NotBlank
        private String phone;

        private String address;
    }
}
