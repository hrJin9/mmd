package com.todos.mmd.auth.application;

import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.auth.application.util.JwtTokenProvider;
import com.todos.mmd.auth.application.dto.TokenDto;
import com.todos.mmd.auth.api.response.MemberResponse;
import com.todos.mmd.auth.application.dto.MemberLoginDto;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberLoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /* 회원가입 */
    @Transactional
    public MemberResponse registerUser(@Valid MemberLoginDto.RegisterMember request) {

        // 1. User 정보
        String pwd = passwordEncoder.encode(request.getPwd());
        Member savedMember = memberRepository.save(request.toMember(pwd));
        // 2. Role 등록
        // TODO: 롤 등록

        return MemberResponse.toVO(savedMember);
    }

    /* 로그인 */
    public TokenDto login(MemberLoginDto.LoginMember request){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPwd());
        // 실제 검증 -> UserDetailService의 loadUserByUsername 메소드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.createToken(authentication);
    }
}
