package com.todos.mmd.auth.application.dto;

import com.todos.mmd.auth.api.request.AuthRequest;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class LoginDto {
    private final String email;
    private final String password;

    public static LoginDto from(AuthRequest.LoginRequest request) {
        return new LoginDto(
                request.getEmail(),
                request.getPassword()
        );
    }

}
