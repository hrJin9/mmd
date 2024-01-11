package com.mmd.application.dto;

import com.mmd.api.request.AuthRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
