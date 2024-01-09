package com.mmd.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class MemberRequest {
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
