package com.mmd.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String DELIMS = " ";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtAuthFilter.doFilterInternal, Jwt 필터 인증 시작");
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 헤더에서 bearer 토큰인지 검증
        String accessToken = isBearerToken(authorization);

        if(StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
            // access 토큰 정보로 Authentication 객체 생성 및 저장
            Authentication authentication = jwtTokenProvider.extractAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String isBearerToken(String authorization){
        return (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) ? authorization.split(DELIMS)[1].trim() : null;
    }
}
