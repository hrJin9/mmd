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
    private UseStauts useStauts;

    public Member(String email, String password, String name, String phone, String address, MemberRole role, UseStauts useStauts) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.useStauts = useStauts;
    }

    public Member(String email, String password, String name, String phone, MemberRole role, UseStauts useStauts) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.useStauts = useStauts;
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
                UseStauts.Y
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
                UseStauts.Y
        );
    }

    public void validatePassword(String password) {
        String hashedPassword = PasswordEncryptor.encrypt(password);
        if(!this.password.equals(password)) {
            throw new AuthException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

    }
}
