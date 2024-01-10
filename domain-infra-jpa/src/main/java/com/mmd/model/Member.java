package com.mmd.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CommonDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 db에 위임
    private Long memberNo;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;

    public Member(String email, String encryptPassword, String name, String phone, String address, MemberRole role, UseStatus useStatus) {
        this.email = email;
        this.password = encryptPassword;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.useStatus = useStatus;
    }

    public static Member of(String email, String encyrptedPassword, String name, String phone, String address) {
        return new Member(
                email,
                encyrptedPassword,
                name,
                phone,
                address,
                MemberRole.USER,
                UseStatus.Y
        );
    }

//    public static Member of(String email, String encyrptedPassword, String name) {
//        return new Member(
//                email,
//                encyrptedPassword,
//                name,
//                null,
//                null,
//                MemberRole.ADMIN,
//                UseStatus.Y
//        );
//    }

    public void update(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
