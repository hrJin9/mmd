package com.mmd.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateDto {
    private Long loginMemberNo;
    private Long requestMemberNo;
    private String name;
    private String phone;
    private String address;

    public static MemberUpdateDto of(Long loginMemberNo, Long requestMemberNo, String name, String phone, String address) {
        return new MemberUpdateDto(
                loginMemberNo,
                requestMemberNo,
                name,
                phone,
                address
        );
    }
}
