package com.mmd.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateDto {
    private final String email;
    private final String password;
    private final String nickName;
    private final String name;
    private final String phone;
    private final String address;
}
