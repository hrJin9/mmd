package com.mmd.entity;

import com.mmd.domain.MemberRole;
import com.mmd.domain.UseStatus;
import com.mmd.service.member.PasswordEncryptor;
import com.mmd.service.member.PasswordValidator;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends CommonDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 db에 위임
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickName;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.USER;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus = UseStatus.IN_USE;

    public static Member of(String email, String password, String name, String nickName, String phone, String address) {
        PasswordValidator.validatePassword(password);
        return Member.builder()
                .email(email)
                .password(PasswordEncryptor.encrypt(password))
                .name(name)
                .nickName(nickName)
                .phone(phone)
                .address(address).build();
    }

    public void update(String nickName, String name, String phone, String address) {
        this.nickName = nickName;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}

