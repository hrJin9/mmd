package com.todos.mmd.auth.application;

import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.auth.domain.MemberRefreshToken;
import com.todos.mmd.global.exception.AuthException;
import com.todos.mmd.repository.member.MemberRefreshTokenRepository;
import com.todos.mmd.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRefreshTokenService {

    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final MemberRepository memberRepository;


    public void saveMemberRefreshToken(String email, String refreshToken) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("존재하지 않는 계정입니다."));

        memberRefreshTokenRepository.findByMemberNo(member.getMemberNo())
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        () -> memberRefreshTokenRepository.save(MemberRefreshToken.from(member, refreshToken))
                );
    }
}
