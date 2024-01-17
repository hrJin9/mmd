package com.mmd.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberUpdateDto {
    private final Long id;
    private final String nickName;
    private final String name;
    private final String phone;
    private final String address;
}
