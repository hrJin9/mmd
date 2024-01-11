package com.mmd.model;

import com.mmd.service.member.PasswordEncryptor;
import com.mmd.service.member.PasswordValidator;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CommonDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 db에 위임
    private Long memberNo;

    private String memberId;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;

    public static Member of(String memberId, String email, String password, String name, String phone, String address) {
        PasswordValidator.validatePassword(password);
        return Member.builder()
                .memberId(memberId)
                .email(email)
                .password(PasswordEncryptor.encrypt(password))
                .name(name)
                .phone(phone)
                .address(address)
                .role(MemberRole.USER)
                .useStatus(UseStatus.IN_USE).build();
    }

    public void update(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
