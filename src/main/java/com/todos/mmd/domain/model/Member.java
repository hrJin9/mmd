package com.todos.mmd.domain.model;

import com.todos.mmd.domain.login.enums.UseStauts;
import com.todos.mmd.domain.login.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name="MEMBER")
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 db에 위임
    private Long memberNo;

    private String email;

    private String pwd;

    private String name;

    private String phone;

    private String address;

    private String registerDate;

    private String lastLoginDate;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private UseStauts useStauts;

}
