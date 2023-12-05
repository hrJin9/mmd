package com.todos.mmd.auth.application;

import com.todos.mmd.auth.api.response.AuthTokenResponse;
import com.todos.mmd.auth.application.dto.AdminCreateDto;
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

    /* 일반회원 회원가입 */
    public String register(MemberCreateDto memberCreateDto) {

        String email = memberCreateDto.getEmail();
        
        if(isDuplicatedEmail(email)) {
            throw new AuthException("중복된 이메일입니다.");
        }

        Member member = Member.from(memberCreateDto);

        log.info("등록된 회원 = {}", member.getEmail());
        return memberRepository.save(member).getEmail();
    }

    /* 관리자 회원가입 */
    public void registerAdmin(AdminCreateDto adminCreateDto) {
        String email = adminCreateDto.getEmail();

        if(isDuplicatedEmail(email)) {
            throw new AuthException("중복된 이메일입니다.");
        }

        Member member = Member.from(adminCreateDto);

        log.info("등록된 관리자 = {}", member.getEmail());
        memberRepository.save(member);
    }

    /* 로그인 */
    public AuthTokenResponse login(LoginDto loginDto){
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new AuthException("존재하지 않는 이메일입니다."));
        
        // 패스워드 검증
        member.validatePassword(loginDto.getPassword());
        return jwtTokenProvider.generate(loginDto.getEmail(), member.getRole().toString());
    }
    
    /* 이메일 중복검사 */
    private boolean isDuplicatedEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

}
