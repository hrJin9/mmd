package com.todos.mmd.auth.application.config;

import com.todos.mmd.auth.application.util.JwtFilter;
import com.todos.mmd.auth.application.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;

    /* TokenProvider와 JwtFilter를 SecurityConfig에 적용 -> JwtFilter를 Security 필터 이전에 실행 */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.debug("JwtSecurityConfig.configure");

        http.addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
