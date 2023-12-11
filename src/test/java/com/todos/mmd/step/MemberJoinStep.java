package com.todos.mmd.step;

import com.todos.mmd.auth.api.request.AuthRequest;
import com.todos.mmd.auth.application.AuthService;
import com.todos.mmd.auth.application.dto.MemberCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class MemberJoinStep {

    @MockBean
    private AuthService authService;


    public static final AuthRequest.MemberCreateRequest MEMBER_CREATE_REQUEST_SUCCESS = new AuthRequest.MemberCreateRequest(
            "test1@google.com", "test123!@#", "테스터1", "010-1111-1111", "서울시"
    );

    public static final AuthRequest.MemberCreateRequest MEMBER_CREATE_REQUEST_DUPLICATED_FAIL = new AuthRequest.MemberCreateRequest(
            "test1@google.com", "test123!@#", "테스터1", "010-1111-1111", "서울시"
    );

    public static final AuthRequest.MemberCreateRequest MEMBER_CREATE_REQUEST_PASSWORD_FAIL = new AuthRequest.MemberCreateRequest(
            "test2@google.com", "test123", "테스터2", "010-1111-1111", "서울시"
    );

    @Test
    public void 일반회원_가입_요청_성공() {
        // given
        authService.register(MemberCreateDto.from(MEMBER_CREATE_REQUEST_SUCCESS));

        //
    }



}
