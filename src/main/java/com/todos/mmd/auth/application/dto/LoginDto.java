package com.todos.mmd.auth.application.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class LoginDto {
    private final String email;
    private final String pwd;
}
