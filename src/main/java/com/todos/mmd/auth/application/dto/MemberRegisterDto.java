package com.todos.mmd.auth.application.dto;

import com.todos.mmd.auth.domain.UseStauts;
import com.todos.mmd.auth.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberRegisterDto {
    private final String email;

    private final String pwd;

    private final String name;

    private final String phone;

    private final String address;

    private final String registerDate;

    private final String lastLoginDate;

    private final UseStauts useStauts;

    public Member of(String pwd){
        return Member.builder()
                .email(email)
                .pwd(pwd)
                .name(name)
                .phone(phone)
                .address(address)
                .registerDate(registerDate)
                .lastLoginDate(lastLoginDate)
                .useStauts(useStauts).build();
    }
}
