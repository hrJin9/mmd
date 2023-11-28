package com.todos.mmd.auth.api.response;

import com.todos.mmd.auth.application.dto.JoinDto;
import com.todos.mmd.auth.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    private final String email;

    public static AuthResponse from(Member member){
        // TODO : 여기서 이렇게 Member 엔티티로 받아와도 되는건가..? dto가 아니라??
        return new AuthResponse(member.getEmail());
    }
}
