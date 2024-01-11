package com.mmd.application;

import com.mmd.api.response.TokenResponse;
import com.mmd.application.dto.LoginDto;
import com.mmd.exception.EmailNotFoundException;
import com.mmd.exception.NotValidTokenException;
import com.mmd.exception.PasswordBadRequestException;
import com.mmd.exception.TokenNotFoundException;
import com.mmd.model.Member;
import com.mmd.model.RefreshToken;
import com.mmd.repository.MemberRepository;
import com.mmd.repository.RefreshTokenRepository;
import com.mmd.security.MemberDetails;
import com.mmd.security.jwt.JwtTokenProvider;
import com.mmd.service.member.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /* 로그인 */
    @Transactional
    public TokenResponse login(LoginDto loginDto){
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("존재하지 않는 이메일입니다."));

        // 비밀번호 일치 여부
        if(!member.getPassword().equals(PasswordEncryptor.encrypt(loginDto.getPassword()))) {
            throw new PasswordBadRequestException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // jwt 토큰 발급
        return jwtTokenProvider.generate(loginDto.getEmail(), member.getRole().toString());
    }

    /* 토큰 재발급 */
    public TokenResponse reissueAccessToken(MemberDetails memberDetails) {
        return jwtTokenProvider.reissueAccessToken(memberDetails.getUsername(), memberDetails.getAuthorities().toString());
    }

    /* 로그아웃 */
    public void logout(String email, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new TokenNotFoundException("이미 로그아웃된 사용자입니다."));
        if(!refreshToken.equals(token.getRefreshToken())) {
            throw new NotValidTokenException("로그인된 사용자의 refresh 토큰이 아닙니다.");
        }

        refreshTokenRepository.delete(token);
    }
}
