package com.todos.mmd.auth.application.dto;

import com.todos.mmd.auth.api.request.AuthRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminCreateDto {
    private final String email;
    private final String password;
    private final String name;
    private final String phone;

    public static AdminCreateDto from(AuthRequest.AdminCreateRequest request) {
        return new AdminCreateDto(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone()
        );
    }

}
