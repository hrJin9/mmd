package com.mmd.oauth.handler;

import com.mmd.application.dto.TokenDto;
import com.mmd.domain.MemberRole;
import com.mmd.oauth.application.dto.CustomOAuth2Member;
import com.mmd.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private static final String DELIMS = " ";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공: OAuth2LoginSuccessHandler.OAuth2LoginSuccessHandler");

        CustomOAuth2Member oAuth2Member = (CustomOAuth2Member) authentication.getPrincipal();

        if (oAuth2Member.getRole().equals(MemberRole.GUEST)) { // ROLE이 GUEST인 경우 회원가입으로 리다이렉트
            response.addHeader(HttpHeaders.AUTHORIZATION, "email" + DELIMS + oAuth2Member.getEmail());
            response.sendRedirect("/api/oauth2/register");
        } else { // ROLE이 GUEST가 아닌 경우, 즉 로그인이 성공한 경우 access/refreshToken 생성
            TokenDto tokenDto = jwtTokenProvider.generate(oAuth2Member.getEmail(), oAuth2Member.getRole().toString());
            response.addHeader(HttpHeaders.AUTHORIZATION, tokenDto.getGrantType() + DELIMS + tokenDto.getAccessToken());
        }

    }

}
