package com.todos.mmd.domain.login.dto;

import com.todos.mmd.domain.model.Member;
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
