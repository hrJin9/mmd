package com.todos.mmd.auth.api.response;

import com.todos.mmd.auth.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private String email;

    public static MemberResponse toVO(Member member){
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.email = member.getEmail();
        return memberResponse;
    }
}
