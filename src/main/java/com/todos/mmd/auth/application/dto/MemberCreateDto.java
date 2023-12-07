package com.todos.mmd.auth.application.dto;

import com.todos.mmd.auth.api.request.AuthRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateDto {
    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String address;

    public static MemberCreateDto from(AuthRequest.MemberCreateRequest request) {
        return new MemberCreateDto(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone(),
                request.getAddress()
        );
    }

}
