package com.todos.mmd.domain.login.dto;

import com.todos.mmd.domain.login.enums.UseStauts;
import com.todos.mmd.domain.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MemberServiceDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RegisterMember {
        @NotNull
        @Email
        private String email;

        @NotNull
        private String pwd;

        @NotBlank
        private String name;

        @NotBlank
        private String phone;

        @NotBlank
        private String address;

        @NotBlank
        private String registerDate;

        @NotBlank
        private String lastLoginDate;

        @NotNull
        private UseStauts useStauts;

        public Member toMember(String pwd){
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LoginMember {
        private String email;
        private String pwd;



    }

}
