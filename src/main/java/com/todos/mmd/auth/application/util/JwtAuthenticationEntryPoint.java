package com.todos.mmd.auth.application.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    /* 유효한 자격증명 제공하지않고 접근하려할 때 401 Unauthorized 에러 */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효한 자격증명이 존재하지 않습니다.");
        resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
    }

}