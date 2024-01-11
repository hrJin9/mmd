package com.mmd.member;

import com.mmd.exception.MemberDuplicatedException;
import com.mmd.exception.MemberNotFoundException;
import com.mmd.exception.NotValidMemberException;
import com.mmd.member.dto.MemberCreateDto;
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


    /* 일반회원 회원가입 */
    public Member register(MemberCreateDto memberCreateDto) {
        if(isDuplicatedEmail(memberCreateDto.getEmail())) {
            throw new MemberDuplicatedException("중복된 이메일입니다.");
        }

        if(isDuplicatedId(memberCreateDto.getMemberId())) {
            throw new MemberDuplicatedException("중복된 아이디입니다.");
        }

        Member member = Member.of(
                memberCreateDto.getEmail(),
                memberCreateDto.getMemberId(),
                memberCreateDto.getPassword(),
                memberCreateDto.getName(),
                memberCreateDto.getPhone(),
                memberCreateDto.getAddress()
        );
        return memberRepository.save(member);
    }

    @Transactional
    public void updateMember(MemberUpdateDto memberUpdateDto) {
        if(!memberUpdateDto.getLoginId().equals(memberUpdateDto.getRequesterId())) {
            throw new NotValidMemberException("로그인 된 사용자에 대한 요청이 아닙니다.");
        }

        Member member = memberRepository.findByMemberId(memberUpdateDto.getLoginId())
                        .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        member.update(
                memberUpdateDto.getName(),
                memberUpdateDto.getPhone(),
                memberUpdateDto.getAddress()
        );
    }

    /* 이메일 중복검사 */
    private boolean isDuplicatedEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }
    /* 아이디 중복검사 */
    private boolean isDuplicatedId(String memberId) { return memberRepository.findByMemberId(memberId).isPresent(); }

}
