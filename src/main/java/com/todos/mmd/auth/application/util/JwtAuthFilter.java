package com.todos.mmd.auth.application.util;

import com.todos.mmd.auth.api.response.AuthTokenResponse;
import com.todos.mmd.auth.application.MemberRefreshTokenService;
import com.todos.mmd.auth.domain.MemberRefreshToken;
import com.todos.mmd.global.exception.AuthException;
import com.todos.mmd.repository.member.MemberRefreshTokenRepository;
import com.todos.mmd.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

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
    private final MemberRefreshTokenService memberRefreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JwtAuthFilter.doFilterInternal, Jwt 필터 인증 시작");

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshTokenHeader = request.getHeader("refreshToken");

        // 헤더에서 bearer 토큰인지 검증
        String accessToken = isBearerToken(authorization);
        String refreshToken = isBearerToken(refreshTokenHeader);

        if(StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
            // 토큰 정보로 Authentication 객체 생성 및 저장
            Authentication authentication = jwtTokenProvider.extractAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (StringUtils.hasText(refreshToken) && !jwtTokenProvider.validateToken(accessToken) && jwtTokenProvider.validateToken(refreshToken)) {
            // 토큰 정보로 Authentication 객체 생성 및 저장
            Authentication authentication = jwtTokenProvider.extractAuthentication(accessToken);
            // accessToken과 refreshToken 재발급
            AuthTokenResponse authTokenResponse = jwtTokenProvider.generate(authentication.getName(), authentication.getAuthorities().toString());
            // TODO: jwt + security flow 다시 한번 살펴봐야 할 듯...
            memberRefreshTokenService.saveMemberRefreshToken(authentication.getName(), authTokenResponse.getRefreshToken());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String isBearerToken(String authorization){
        return (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) ? authorization.split(DELIMS)[1].trim() : null;
    }

}
