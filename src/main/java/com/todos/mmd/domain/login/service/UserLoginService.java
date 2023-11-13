package com.todos.mmd.domain.login.service;

import com.todos.mmd.jwt.security.config.JwtTokenProvider;
import com.todos.mmd.jwt.security.dto.TokenDto;
import com.todos.mmd.domain.login.dto.UserResponse;
import com.todos.mmd.domain.login.dto.UserServiceDto;
import com.todos.mmd.domain.model.User;
import com.todos.mmd.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /* 회원가입 */
    @Transactional
    public UserResponse registerUser(@Valid UserServiceDto.RegisterUser registerUser) {

        // 1. User 정보
        String pwd = passwordEncoder.encode(registerUser.getPwd());
        User savedUser = userRepository.save(registerUser.toUser(pwd));
        // 2. Role 등록
        // TODO: 롤 등록

        return UserResponse.toVO(savedUser);
    }

    /* 로그인 */
    public TokenDto login(UserServiceDto.LoginUser loginUser){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPwd());
        // 실제 검증 -> UserDetailService의 loadUserByUsername 메소드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.createToken(authentication);
    }
}
