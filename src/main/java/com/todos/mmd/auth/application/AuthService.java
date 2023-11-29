package com.todos.mmd.auth.application;

import com.todos.mmd.auth.api.response.AuthTokenResponse;
import com.todos.mmd.auth.application.dto.MemberCreateDto;
import com.todos.mmd.auth.application.util.JwtTokenProvider;
import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.auth.application.dto.LoginDto;
import com.todos.mmd.global.exception.AuthException;
import com.todos.mmd.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /* 회원가입 */
    public String register(MemberCreateDto serviceDto) {

        String email = serviceDto.getEmail();
        
        if(isDuplicatedEmail(email)) {
            throw new AuthException("중복된 이메일입니다.");
        }

        Member member = Member.from(serviceDto);
        return memberRepository.save(member).getEmail();
    }

    /* 로그인 */
    public AuthTokenResponse login(LoginDto serviceDto){
        Member member = memberRepository.findByEmail(serviceDto.getEmail())
                .orElseThrow(() -> new AuthException("존재하지 않는 이메일입니다."));
        
        // 패스워드 검증
        member.validatePassword(serviceDto.getPassword());
        return jwtTokenProvider.generate(serviceDto.getEmail(), member.getRole());
    }
    
    /* 이메일 중복검사 */
    private boolean isDuplicatedEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }
}
