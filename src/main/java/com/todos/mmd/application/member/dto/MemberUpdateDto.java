package com.todos.mmd.application.member.dto;

import com.todos.mmd.api.member.request.MemberRequest;
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

    public static MemberUpdateDto of(MemberRequest.MemberUpdateRequest request, Long loginMemberNo, Long requestMemberNo) {
        return new MemberUpdateDto(
                loginMemberNo,
                requestMemberNo,
                request.getName(),
                request.getPhone(),
                request.getAddress()
        );
    }
}
