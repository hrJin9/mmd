package com.todos.mmd.auth.domain;

import com.todos.mmd.auth.application.dto.AdminCreateDto;
import com.todos.mmd.auth.application.dto.MemberCreateDto;
import com.todos.mmd.auth.application.util.PasswordEncryptor;
import com.todos.mmd.auth.application.util.PasswordValidator;
import com.todos.mmd.entity.CommonDate;
import com.todos.mmd.global.exception.AuthException;
import lombok.*;

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

    public Member(String email, String password, String name, String phone, String address, MemberRole role, UseStatus useStatus) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.useStatus = useStatus;
    }

    public Member(String email, String password, String name, String phone, MemberRole role, UseStatus useStatus) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.useStatus = useStatus;
    }

    public static Member from(MemberCreateDto serviceDto) {
        PasswordValidator.validatePassword(serviceDto.getPassword());
        return new Member(
                serviceDto.getEmail(),
                PasswordEncryptor.encrypt(serviceDto.getPassword()),
                serviceDto.getName(),
                serviceDto.getPhone(),
                serviceDto.getAddress(),
                MemberRole.USER,
                UseStatus.Y
        );
    }

    public static Member from(AdminCreateDto adminCreateDto) {
        PasswordValidator.validatePassword(adminCreateDto.getPassword());
        return new Member(
                adminCreateDto.getEmail(),
                PasswordEncryptor.encrypt(adminCreateDto.getPassword()),
                adminCreateDto.getName(),
                adminCreateDto.getPhone(),
                MemberRole.ADMIN,
                UseStatus.Y
        );
    }

    public void validatePassword(String password) {
        String hashedPassword = PasswordEncryptor.encrypt(password);
        if(!this.password.equals(hashedPassword)) {
            throw new AuthException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

    }
}
