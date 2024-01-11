package com.mmd.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateDto {
    private String loginId;
    private String name;
    private String phone;
    private String address;
}
