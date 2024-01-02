package com.todos.mmd.auth.application;

import com.todos.mmd.auth.api.response.TokenResponse;
import com.todos.mmd.auth.application.dto.AdminCreateDto;
import com.todos.mmd.auth.application.dto.MemberCreateDto;
import com.todos.mmd.auth.application.util.JwtTokenProvider;
import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.auth.application.dto.LoginDto;
import com.todos.mmd.auth.domain.RefreshToken;
import com.todos.mmd.auth.exception.JwtException;
import com.todos.mmd.global.exception.EmailException;
import com.todos.mmd.repository.member.MemberRepository;
import com.todos.mmd.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /* 일반회원 회원가입 */
    public Member register(MemberCreateDto memberCreateDto) {
        String email = memberCreateDto.getEmail();
        
        if(isDuplicatedEmail(email)) {
            throw new EmailException("중복된 이메일입니다.");
        }

        Member member = Member.from(memberCreateDto);
        return memberRepository.save(member);
    }

    /* 관리자 회원가입 */
    public Member registerAdmin(AdminCreateDto adminCreateDto) {
        String email = adminCreateDto.getEmail();

        if(isDuplicatedEmail(email)) {
            throw new EmailException("중복된 이메일입니다.");
        }

        Member member = Member.from(adminCreateDto);
        return memberRepository.save(member);
    }

    /* 로그인 */
    @Transactional
    public TokenResponse login(LoginDto loginDto){
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new EmailException("존재하지 않는 이메일입니다."));

        // 패스워드 검증
        member.validatePassword(loginDto.getPassword());

        // jwt 토큰 발급
        TokenResponse tokenResponse = jwtTokenProvider.generate(loginDto.getEmail(), member.getRole().toString());

        // redis에 refreshToken 저장
        refreshTokenRepository.save(RefreshToken.of(member.getEmail(), tokenResponse.getRefreshToken(), tokenResponse.getExpiresIn()));

        return tokenResponse;
    }
    
    /* 이메일 중복검사 */
    private boolean isDuplicatedEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    /* 토큰 재발급 */
    public TokenResponse reissueAccessToken(MemberDetails memberDetails) {
        return jwtTokenProvider.reissueAccessToken(memberDetails.getUsername(), memberDetails.getAuthorities().toString());
    }

    /* 로그아웃 */
    public void logout(String email, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new JwtException("이미 로그아웃된 사용자입니다."));
        if(!refreshToken.equals(token.getRefreshToken())) {
            throw new JwtException("로그인된 사용자의 Refresh 토큰이 아닙니다.");
        }

        refreshTokenRepository.delete(token);
    }

}
