package com.mmd.application.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmd.ExceptionResponse;
import com.mmd.exception.AuthDeniedException;
import com.mmd.exception.ExpiredTokenException;
import com.mmd.exception.NotValidTokenException;
import com.mmd.exception.TokenNotExistsException;
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
        } catch (TokenNotExistsException | ExpiredTokenException | NotValidTokenException e) {
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
