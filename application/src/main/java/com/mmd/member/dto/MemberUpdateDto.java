package com.mmd.member.dto;

import com.mmd.security.util.ClientMemberLoader;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateDto {
    private String loginId;
    private String requesterId;
    private String name;
    private String phone;
    private String address;

    public static MemberUpdateDto of(String requesterId, String name, String phone, String address) {
        return new MemberUpdateDto(
                ClientMemberLoader.getClientId(),
                requesterId,
                name,
                phone,
                address
        );
    }
}
