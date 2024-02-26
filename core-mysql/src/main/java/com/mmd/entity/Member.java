package com.mmd.entity;

import com.mmd.domain.MemberRole;
import com.mmd.domain.SocialType;
import com.mmd.util.PasswordEncryptor;
import com.mmd.util.PasswordValidator;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted_date = CURRENT_TIMESTAMP WHERE member_id = ?") // soft delete
@Where(clause = "deleted_date is null") // delete 되지 않은것만 조회
public class Member extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 db에 위임e
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickName;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    @Builder
    public Member(String email, String password, String name, String nickName, String phone, String address, MemberRole role, SocialType socialType, String socialId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
    }

    public static Member of(String email, String password, String name, String nickName, String phone, String address) {
        PasswordValidator.validatePassword(password);
        return Member.builder()
                .email(email)
                .password(PasswordEncryptor.encrypt(password))
                .name(name)
                .nickName(nickName)
                .phone(phone)
                .address(address)
                .role(MemberRole.USER).build();
    }

    public void update(String nickName, String name, String phone, String address) {
        this.nickName = nickName;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public void updateRole(MemberRole role) {
        this.role = role;
    }
}

