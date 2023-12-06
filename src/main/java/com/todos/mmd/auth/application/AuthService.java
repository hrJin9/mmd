package com.todos.mmd.auth.application;

import com.todos.mmd.auth.api.response.TokenResponse;
import com.todos.mmd.auth.application.dto.AdminCreateDto;
import com.todos.mmd.auth.application.dto.MemberCreateDto;
import com.todos.mmd.auth.application.util.JwtTokenProvider;
import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.auth.application.dto.LoginDto;
import com.todos.mmd.global.exception.AuthException;
import com.todos.mmd.repository.member.MemberRepository;
import com.todos.mmd.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /* 일반회원 회원가입 */
    public void register(MemberCreateDto memberCreateDto) {

        String email = memberCreateDto.getEmail();
        
        if(isDuplicatedEmail(email)) {
            throw new AuthException("중복된 이메일입니다.");
        }

        Member member = Member.from(memberCreateDto);

        log.info("등록된 회원 = {}", member.getEmail());
        memberRepository.save(member).getEmail();
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
    @Transactional
    public TokenResponse login(LoginDto loginDto){
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new AuthException("존재하지 않는 이메일입니다."));
        
        // 패스워드 검증
        member.validatePassword(loginDto.getPassword());

        // jwt 토큰 발급
        TokenResponse tokenResponse = jwtTokenProvider.generate(loginDto.getEmail(), member.getRole().toString());

        redisRepository.setValues(member.getEmail(), tokenResponse.getRefreshToken());

        return tokenResponse;
    }
    
    /* 이메일 중복검사 */
    private boolean isDuplicatedEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    /* 토큰 재발급 */
    public TokenResponse reissue(UserDetailsImpl userDetails) {
        return jwtTokenProvider.reissueAccessToken(userDetails.getUsername(), userDetails.getAuthorities().toString());
    }

    /* 로그아웃 */
    public void logout(String email, String refreshToken) {

        if(!redisRepository.isExists(email)) {
            throw new AuthException("이미 로그아웃된 계정입니다.");
        } else if (!redisRepository.Matches(email, refreshToken)){
            throw new AuthException("로그인된 사용자의 refresh token이 아닙니다.");
        }

        redisRepository.deleteValues(email);
    }
}
