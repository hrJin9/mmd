package com.todos.mmd.auth.application.dto;

import lombok.*;

@Getter
public class MemberLoginDto {

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class LoginMember {
        private final String email;
        private final String pwd;
    }

}
