package com.mmd.security;

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

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private static final String[] ALLOWED_URIS = {"/api/auth/**", "/api/member/register"};

    /* filterChain 설정 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 토큰 사용 -> 비활성화
            .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
                .sessionManagement()  //세션 사용 X
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ALLOWED_URIS).permitAll()
                .antMatchers("/swagger-resources/**", "/swagger-ui/**", "swagger/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthFilter.class);

        return http.build();
    }
}
