package com.todos.mmd.application.member;

import com.todos.mmd.application.member.dto.MemberUpdateDto;
import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.auth.exception.AuthException;
import com.todos.mmd.global.exception.ForbiddenException;
import com.todos.mmd.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMember(MemberUpdateDto memberUpdateDto) {
        if(!memberUpdateDto.getLoginMemberNo().equals(memberUpdateDto.getRequestMemberNo())) {
            throw new ForbiddenException("로그인 된 사용자에 대한 요청이 아닙니다.");
        }

        Member member = memberRepository.findById(memberUpdateDto.getLoginMemberNo())
                        .orElseThrow(() -> new AuthException("존재하지 않는 회원입니다."));

        member.update(memberUpdateDto);
    }
}
