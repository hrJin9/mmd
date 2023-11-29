package com.todos.mmd.auth.application.dto;

import com.todos.mmd.auth.api.request.AuthRequest;
import com.todos.mmd.auth.domain.UseStauts;
import com.todos.mmd.auth.domain.Member;
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
    private final String registerDate;
    private final String lastLoginDate;
    private final UseStauts useStauts;

    public static MemberCreateDto from(AuthRequest.MemberCreateRequest request) {
        return new MemberCreateDto(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone(),
                request.getAddress(),
                request.getRegisterDate(),
                request.getLastLoginDate(),
                request.getUseStauts()
        );
    }

}
