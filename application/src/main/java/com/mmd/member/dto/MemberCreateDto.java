package com.mmd.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateDto {
    private final String memberId;
    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String address;

}
