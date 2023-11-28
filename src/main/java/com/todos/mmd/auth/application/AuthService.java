package com.todos.mmd.auth.application;

import com.todos.mmd.auth.application.dto.JoinDto;
import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.auth.application.util.JwtTokenProvider;
import com.todos.mmd.auth.application.dto.TokenDto;
import com.todos.mmd.auth.api.response.AuthResponse;
import com.todos.mmd.auth.application.dto.LoginDto;
import com.todos.mmd.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /* 회원가입 */
    public AuthResponse join(JoinDto serviceDto) {
        String pwd = passwordEncoder.encode(serviceDto.getPwd());
        Member member = serviceDto.toEntity(pwd);

        Member savedMember = memberRepository.save(member);
        return AuthResponse.from(savedMember);
    }

    /* 로그인 */
    public TokenDto login(LoginDto.LoginMember request){

    }

}
