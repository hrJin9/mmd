package com.mmd.application;

import com.mmd.application.dto.LoginDto;
import com.mmd.application.dto.TokenDto;
import com.mmd.exception.EmailNotFoundException;
import com.mmd.exception.TokenNotValidException;
import com.mmd.exception.PasswordBadRequestException;
import com.mmd.exception.TokenNotFoundException;
import com.mmd.entity.Member;
import com.mmd.entity.RefreshToken;
import com.mmd.repository.MemberRepository;
import com.mmd.repository.RefreshTokenRepository;
import com.mmd.security.MemberDetails;
import com.mmd.security.jwt.JwtTokenProvider;
import com.mmd.util.PasswordEncryptor;
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
    public TokenDto login(LoginDto loginDto){
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
    public TokenDto reissueAccessToken(MemberDetails memberDetails) {
        return jwtTokenProvider.reissueAccessToken(memberDetails.getUsername(), memberDetails.getAuthorities().toString());
    }

    /* 로그아웃 */
    public void logout(String email, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new TokenNotFoundException("이미 로그아웃된 사용자입니다."));
        if(!refreshToken.equals(token.getRefreshToken())) {
            throw new TokenNotValidException("로그인된 사용자의 refresh 토큰이 아닙니다.");
        }

        refreshTokenRepository.delete(token);
    }
}
