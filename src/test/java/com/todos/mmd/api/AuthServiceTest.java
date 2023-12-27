package com.todos.mmd.api;

import com.todos.mmd.auth.api.request.AuthRequest;
import com.todos.mmd.auth.application.AuthService;
import com.todos.mmd.auth.application.dto.MemberCreateDto;
import com.todos.mmd.auth.application.util.JwtTokenProvider;
import com.todos.mmd.auth.domain.Member;
import com.todos.mmd.global.exception.EmailException;
import com.todos.mmd.repository.member.MemberRepository;
import com.todos.mmd.repository.redis.RefreshTokenRepository;
import com.todos.mmd.support.ServiceTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("회원가입/로그인 서비스 테스트")
@ServiceTest
class AuthServiceTest {
    @MockBean
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @AfterEach
    public void cleanUp() {memberRepository.deleteAllInBatch();}

    @Test
    public void 정상적인_회원을_저장한다() {
        // given
        final AuthRequest.MemberCreateRequest 새로운_회원 = new AuthRequest.MemberCreateRequest(
                "tester@google.com",
                "tester123!@#",
                "테스터",
                "01011112222",
                "테스터 주소"
        );

        // when
        final Member 저장된_회원 = authService.register(MemberCreateDto.from(새로운_회원));

        // then
        assertThat(새로운_회원.getEmail()).isEqualTo(저장된_회원.getEmail());
        assertThat(새로운_회원.getName()).isEqualTo(저장된_회원.getName());
    }

    @Test
    public void 관리자_가입_요청_성공() {

    }

    @Test
    public void 주소를_제외한_모든값들은_포함되어야_한다() {
        // given
//        authService.register(MemberCreateDto.from(일반회원_가입요청));

    }

    @Test
    public void 비밀번호는_8자이상_15자이하로_영어숫자특수문자를_포함해야한다() {

    }


    @Test
    public void 중복_이메일은_등록할_수_없다() {
        // given
        final AuthRequest.MemberCreateRequest 중복된_새로운_회원 = new AuthRequest.MemberCreateRequest(
                "tester@google.com",
                "tester123!@#",
                "테스터2",
                "01011112222",
                "테스터 주소"
        );

        // when
        assertThatThrownBy(() -> authService.register(MemberCreateDto.from(중복된_새로운_회원)))
                .isInstanceOf(EmailException.class)
                .hasMessage("중복된 이메일입니다.");
    }
}