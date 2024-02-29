package com.mmd.security.jwt;

import com.mmd.exception.TokenNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /* 유효한 자격증명 제공하지않고 접근하려할 때 401 Unauthorized 에러 */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        throw new TokenNotFoundException("토큰 정보가 존재하지 않습니다.");
    }
}