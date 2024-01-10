package com.mmd.application.util;

import com.mmd.exception.AuthDeniedException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /* 인가 -> 필요한 권한이 존재하지 않는 경우 403 Forbidden 에러 */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        throw new AuthDeniedException("필요한 권한이 존재하지 않습니다.");
    }
}