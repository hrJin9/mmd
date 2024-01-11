package com.mmd.member;

import com.mmd.exception.MemberDuplicatedException;
import com.mmd.exception.MemberNotFoundException;
import com.mmd.member.dto.MemberCreateDto;
import com.mmd.member.dto.MemberUpdateDto;
import com.mmd.model.Member;
import com.mmd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /* 일반회원 회원가입 */
    @Transactional(readOnly = true)
    public Member register(MemberCreateDto memberCreateDto) {
        // 아이디, 이메일 중복 검사
        isDuplicated(memberCreateDto.getMemberId(), memberCreateDto.getEmail());

        Member member = Member.of(
                memberCreateDto.getMemberId(),
                memberCreateDto.getEmail(),
                memberCreateDto.getPassword(),
                memberCreateDto.getName(),
                memberCreateDto.getPhone(),
                memberCreateDto.getAddress()
        );

        return memberRepository.save(member);
    }

    @Transactional
    public void updateMember(MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findByMemberId(memberUpdateDto.getLoginId())
                        .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        member.update(
                memberUpdateDto.getName(),
                memberUpdateDto.getPhone(),
                memberUpdateDto.getAddress()
        );
    }

    /* 아이디, 이메일 중복검사 */
    private void isDuplicated(String memberId, String email) {
        if(memberRepository.findByMemberId(memberId).isEmpty()) {
            throw new MemberDuplicatedException("중복된 아이디입니다.");
        } else if(memberRepository.findByEmail(email).isEmpty()) {
            throw new MemberDuplicatedException("중복된 이메일입니다.");
        }
    }

}
