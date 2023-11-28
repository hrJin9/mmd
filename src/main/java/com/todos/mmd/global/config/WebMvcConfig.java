package com.todos.mmd.global.config;

import com.todos.mmd.auth.application.util.JwtAccessDeniedHandler;
import com.todos.mmd.auth.application.util.JwtAuthentcationEntryPoint;
import com.todos.mmd.auth.application.config.JwtSecurityConfig;
import com.todos.mmd.auth.application.util.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // 유저 권한에 따라 접근 가능한 메소드 제한 -> @PreAuthorize 사용하기 위함
public class WebMvcConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthentcationEntryPoint jwtAuthentcationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    public WebMvcConfig(JwtTokenProvider jwtTokenProvider, JwtAuthentcationEntryPoint jwtAuthentcationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthentcationEntryPoint = jwtAuthentcationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* filterChain 설정 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 토큰 사용 -> 비활성화
            .exceptionHandling()
                .authenticationEntryPoint(jwtAuthentcationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .sessionManagement()  //세션 사용 X
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
            .and()
            .apply(new JwtSecurityConfig(jwtTokenProvider)); //JwtFilter를 등록했던 jwtSecurityConfig 적용

        return http.build();
    }



}
