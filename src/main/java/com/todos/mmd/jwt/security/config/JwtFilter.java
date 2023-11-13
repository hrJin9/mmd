package com.todos.mmd.jwt.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    /* filtering 로직 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("JwtFilter.doFilter");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = jwtTokenProvider.resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        // 토큰 유효성 검증
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("SecurityContext에 '{}' 인증정보 저장, URI : {}", authentication.getName(), requestURI);
        } else {
            log.debug("유효한 Jwt 토큰이 없음, URI : {}" , requestURI);
        }

        chain.doFilter(request, response);
    }

}
