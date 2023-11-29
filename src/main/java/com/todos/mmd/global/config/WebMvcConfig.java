package com.todos.mmd.global.config;

import com.todos.mmd.auth.application.util.JwtAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    private final JwtTokenProvider jwtTokenProvider;
//    private final JwtAuthentcationEntryPoint jwtAuthentcationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final JwtAuthInterceptor jwtAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("api/auth/token/login/**")
                .excludePathPatterns("api/auth/token/join/**");
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /* filterChain 설정 */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable() // 토큰 사용 -> 비활성화
//            .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthentcationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//            .and()
//            .sessionManagement()  //세션 사용 X
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeRequests()
//                .antMatchers("/api/auth/token/login", "/api/auth/token/join").permitAll()
//                .anyRequest().authenticated()
//            .and()
//            .addFilterBefore()
////            .apply(new JwtSecurityConfig(jwtTokenProvider)); //JwtFilter를 등록했던 jwtSecurityConfig 적용
//
//        return http.build();
//    }


}
