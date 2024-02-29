package com.mmd.security;

import com.mmd.exception.EmailNotFoundException;
import com.mmd.entity.Member;
import com.mmd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("존재하지 않는 계정입니다."));
        return new MemberDetails(member);
    }
}
