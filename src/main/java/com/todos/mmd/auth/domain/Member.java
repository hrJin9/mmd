package com.todos.mmd.auth.domain;

import com.todos.mmd.auth.application.dto.MemberCreateDto;
import com.todos.mmd.auth.application.util.PasswordEncryptor;
import com.todos.mmd.auth.application.util.PasswordValidator;
import com.todos.mmd.auth.domain.UseStauts;
import com.todos.mmd.auth.domain.MemberRole;
import com.todos.mmd.global.exception.AuthException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 db에 위임
    private Long memberNo;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String address;

    private String registerDate;

    private String lastLoginDate;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private UseStauts useStauts;

    public static Member from(MemberCreateDto serviceDto) {
        PasswordValidator.validatePassword(serviceDto.getPassword());
        return new Member(
                null,
                serviceDto.getEmail(),
                PasswordEncryptor.encrypt(serviceDto.getPassword()),
                serviceDto.getName(),
                serviceDto.getPhone(),
                serviceDto.getAddress(),
                serviceDto.getRegisterDate(),
                serviceDto.getLastLoginDate(),
                MemberRole.USER,
                UseStauts.Y
        );
    }

    public void validatePassword(String password) {
        String hashedPassword = PasswordEncryptor.encrypt(password);
        if(!this.password.equals(password)) {
            throw new AuthException("아이디와 패스워드를 다시 확인해주세요.");
        }

    }
}
