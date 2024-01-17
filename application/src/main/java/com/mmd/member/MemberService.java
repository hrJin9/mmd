package com.mmd.member;

import com.mmd.exception.MemberDuplicatedException;
import com.mmd.exception.MemberNotFoundException;
import com.mmd.member.dto.MemberCreateDto;
import com.mmd.member.dto.MemberUpdateDto;
import com.mmd.entity.Member;
import com.mmd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /* 일반회원 회원가입 */
    @Transactional
    public void register(MemberCreateDto memberCreateDto) {
        // 이메일 중복 검사
        if(isDuplicated(memberCreateDto.getEmail()))
            throw new MemberDuplicatedException("이미 존재하는 이메일입니다.");

        Member member = Member.of(
                memberCreateDto.getEmail(),
                memberCreateDto.getPassword(),
                memberCreateDto.getNickName(),
                memberCreateDto.getName(),
                memberCreateDto.getPhone(),
                memberCreateDto.getAddress()
        );

        memberRepository.save(member);
    }

    @Transactional
    public void updateMember(MemberUpdateDto memberUpdateDto) {
        Member member = findMember(memberUpdateDto.getId());
        member.update(
                member.getNickName(),
                memberUpdateDto.getName(),
                memberUpdateDto.getPhone(),
                memberUpdateDto.getAddress()
        );
    }

    /* 이메일 중복검사 */
    private boolean isDuplicated(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }
    
    /* 회원 조회 */
    @Transactional
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
    }

}
