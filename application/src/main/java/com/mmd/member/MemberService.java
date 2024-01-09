package com.mmd.member;

import com.mmd.exception.UnAuthorizedException;
import com.mmd.exception.UserNotFoundException;
import com.mmd.member.dto.MemberUpdateDto;
import com.mmd.model.Member;
import com.mmd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMember(MemberUpdateDto memberUpdateDto) {
        if(!memberUpdateDto.getLoginMemberNo().equals(memberUpdateDto.getRequestMemberNo())) {
            throw new UnAuthorizedException("로그인 된 사용자에 대한 요청이 아닙니다.");
        }

        Member member = memberRepository.findById(memberUpdateDto.getLoginMemberNo())
                        .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        member.update(
                memberUpdateDto.getName(),
                memberUpdateDto.getPhone(),
                memberUpdateDto.getAddress()
        );
    }
}
