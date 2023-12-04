package com.todos.mmd.global.config;

import com.todos.mmd.auth.application.util.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    /* filterChain 설정 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 토큰 사용 -> 비활성화
                // TODO : 왜 register 주소가 Security Filter를 타는건지..
//            .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthentcationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//            .and()
                .sessionManagement()  //세션 사용 X
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/token/login", "/api/auth/token/register").permitAll()
//                .antMatchers("/swagger-resources/**", "/swagger-ui.html", "swagger/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
