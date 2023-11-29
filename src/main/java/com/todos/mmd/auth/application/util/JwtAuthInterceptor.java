package com.todos.mmd.auth.application.util;

import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.global.exception.AuthException;
import com.todos.mmd.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private static final String DELIMS = " ";
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        // 헤더에서 bearer 토큰인지 검증
        String accessToken = isBearerToken(authorization);
        if(!StringUtils.hasText(accessToken) && !jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthException("인증할 수 없는 토큰입니다.");
        }

        String email = jwtTokenProvider.extractSubject(accessToken);
        memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("존재하지 않는 이메일입니다."));

        // 토큰 정보로 Authentication 객체 생성 및 저장
        Authentication authentication = jwtTokenProvider.extractAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }

    private String isBearerToken(String authorization){
        return (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) ? authorization.split(DELIMS)[1].trim() : null;
    }

}
