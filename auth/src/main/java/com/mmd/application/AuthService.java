package com.mmd.application;

import com.mmd.repository.RefreshTokenRepository;
import com.mmd.api.response.TokenResponse;
import com.mmd.application.dto.LoginDto;
import com.mmd.application.dto.MemberCreateDto;
import com.mmd.application.util.JwtTokenProvider;
import com.mmd.application.util.password.PasswordEncryptor;
import com.mmd.application.util.password.PasswordValidator;
import com.mmd.exception.EmailCheckException;
import com.mmd.exception.ExpiredTokenException;
import com.mmd.exception.NotValidTokenException;
import com.mmd.exception.PasswordBadRequestException;
import com.mmd.model.Member;
import com.mmd.model.RefreshToken;
import com.mmd.repository.MemberRepository;
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
            throw new EmailCheckException("중복된 이메일입니다.");
        }

        PasswordValidator.validatePassword(memberCreateDto.getPassword());
        Member member = Member.of(
                memberCreateDto.getEmail(),
                PasswordEncryptor.encrypt(memberCreateDto.getPassword()),
                memberCreateDto.getName(),
                memberCreateDto.getPhone(),
                memberCreateDto.getAddress()
        );
        return memberRepository.save(member);
    }

    /* 관리자 회원가입 */
//    public Member registerAdmin(AdminCreateDto adminCreateDto) {
//        String email = adminCreateDto.getEmail();
//
//        if(isDuplicatedEmail(email)) {
//            throw new EmailCheckException("중복된 이메일입니다.");
//        }
//
//        PasswordValidator.validatePassword(adminCreateDto.getPassword());
//        Member member = Member.of(
//                adminCreateDto.getEmail(),
//                PasswordEncryptor.encrypt(adminCreateDto.getPassword()),
//                adminCreateDto.getName()
//        );
//        return memberRepository.save(member);
//    }

    /* 로그인 */
    @Transactional
    public TokenResponse login(LoginDto loginDto){
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new EmailCheckException("존재하지 않는 이메일입니다."));

        // 비밀번호 일치 여부
        if(!member.getPassword().equals(PasswordEncryptor.encrypt(loginDto.getPassword()))) {
            throw new PasswordBadRequestException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // jwt 토큰 발급
        return jwtTokenProvider.generate(loginDto.getEmail(), member.getRole().toString());
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
                .orElseThrow(() -> new ExpiredTokenException("이미 로그아웃된 사용자입니다."));
        if(!refreshToken.equals(token.getRefreshToken())) {
            throw new NotValidTokenException("로그인된 사용자의 refresh 토큰이 아닙니다.");
        }

        refreshTokenRepository.delete(token);
    }
}
