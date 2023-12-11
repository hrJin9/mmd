package com.todos.mmd.auth.application.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todos.mmd.auth.exception.AuthDeniedException;
import com.todos.mmd.auth.exception.JwtException;
import com.todos.mmd.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            sendResponse(response, HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (AuthDeniedException e) {
            sendResponse(response, HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    private void sendResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(message, LocalDateTime.now())));
    }

}
