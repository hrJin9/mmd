package com.mmd.security;

import com.mmd.oauth2.OAuth2Service;
import com.mmd.oauth2.handler.OAuth2FaliureHandler;
import com.mmd.oauth2.handler.OAuth2SuccessHandler;
import com.mmd.security.jwt.JwtAccessDeniedHandler;
import com.mmd.security.jwt.JwtAuthFilter;
import com.mmd.security.jwt.JwtAuthenticationEntryPoint;
import com.mmd.security.jwt.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2Service oAuth2Service;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FaliureHandler oAuth2FaliureHandler;
    private static final String[] ALLOWED_URIS = {"/api/auth/**", "/api/member/register", "/api/oauth2/register"};
    private static final String[] SWAGGER_URIS = {"/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "swagger/**", "/swagger-ui.html"};

    /* filterChain 설정 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable() // CRSF : cross-site-request-forgery 웹 브라우저가 신뢰할 수 없는 악성 사이트에서 사용자가 원치않는 작업을 수행하는 공격, Rest API -> 쿠케이 의존하지 않고, 브라우저로 request를 받지 않는 OAuth2, JWT를 사용하므로 불필요
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement()  //세션 사용 X
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 요청에 대한 인가 처리
                .antMatchers(ALLOWED_URIS).permitAll()
                .antMatchers(SWAGGER_URIS).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FaliureHandler)
                .userInfoEndpoint().userService(oAuth2Service);

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthFilter.class);

        return http.build();
    }
}
