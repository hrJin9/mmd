package com.mmd;

import com.mmd.application.AuthService;
import com.mmd.exception.TokenExpiredException;
import com.mmd.exception.TokenNotValidException;
import com.mmd.model.RefreshToken;
import com.mmd.repository.RefreshTokenRepository;
import com.mmd.api.response.TokenResponse;
import com.mmd.security.jwt.JwtTokenProvider;
import com.mmd.model.Member;
import com.mmd.model.MemberRole;
import com.mmd.model.UseStatus;
import com.mmd.repository.MemberRepository;
import com.mmd.service.member.PasswordEncryptor;
import com.mmd.support.ServiceTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@DisplayName("회원가입/로그인 서비스 테스트")
@ServiceTest
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private static Member existMember;

    @BeforeAll
    static void setUp() {
        existMember = new Member("tester@gmail.com", PasswordEncryptor.encrypt("tester123!@#"), "테스터", "01011112222", "테스터주소", MemberRole.USER, UseStatus.Y);
    }

    @Test
    public void 이미_존재하는_이메일이면_예외를_던진다() {
        // given
        MemberCreateDto 새로운_회원 = new MemberCreateDto(
                "tester@gmail.com",
                "test123123#",
                "테스터",
                "01011112222",
                "테스터 주소"
        );

        given(memberRepository.findByEmail(existMember.getEmail()))
                .willReturn(Optional.of(existMember));

        // when, then
        assertThatThrownBy(() -> authService.register(새로운_회원))
                .isInstanceOf(EmailCheckException.class)
                .hasMessage("중복된 이메일입니다.");
    }

    @Test
    public void 정상적인_회원을_저장한다() {
        // given
        MemberCreateDto 새로운_회원 = new MemberCreateDto(
                "newtester@gmail.com",
                "test123123#",
                "테스터",
                "01011112222",
                "테스터 주소"
        );

        // when
        when(memberRepository.save(any())).thenReturn(Member.of(
                새로운_회원.getEmail(),
                PasswordEncryptor.encrypt(새로운_회원.getPassword()),
                새로운_회원.getName(),
                새로운_회원.getPhone(),
                새로운_회원.getAddress()
        ));
        Member savedMember = authService.register(새로운_회원);

        // then
        then(memberRepository).should(times(1)).save(any());
        assertThat(savedMember.getEmail()).isEqualTo(새로운_회원.getEmail());
    }

   @Test
   public void 이메일이_존재하지_않으면_예외를_던진다() {
       // given
       LoginDto 로그인_회원 = new LoginDto("notester@gmail.com", "tester123!@#");

       given(memberRepository.findByEmail(로그인_회원.getEmail()))
               .willReturn(Optional.empty());

       // when, then
       assertThatThrownBy(() -> authService.login(로그인_회원))
               .isInstanceOf(EmailCheckException.class)
               .hasMessage("존재하지 않는 이메일입니다.");
   }

    @Test
    public void 비밀번호가_일치하지_않으면_예외를_던진다() {
        // given
        LoginDto 로그인_회원 = new LoginDto("tester@gmail.com", "wrongPassword");

        given(memberRepository.findByEmail(existMember.getEmail()))
            .willReturn(Optional.of(existMember));

        // when, then
        assertThatThrownBy(() -> authService.login(로그인_회원))
                .isInstanceOf(PasswordBadRequestException.class)
                .hasMessage("이메일 또는 비밀번호가 일치하지 않습니다.");
    }

    @Test
    public void 비밀번호가_일치하면_토큰을_반환한다() {
        // given
        LoginDto 로그인_회원 = new LoginDto("tester@gmail.com", "tester123!@#");

        given(memberRepository.findByEmail(existMember.getEmail()))
                .willReturn(Optional.of(existMember));
        given(jwtTokenProvider.generate(existMember.getEmail(), existMember.getRole().toString()))
                .willReturn(new TokenResponse("atoken", "rtoken", "b", 1L));

        // when
        TokenResponse response = authService.login(로그인_회원);

        // then
//        then(refreshTokenRepository).should(times(1)).save(any());
        assertThat(response.getAccessToken()).isEqualTo("atoken");
    }

    @Test
    public void 억세스토큰_재발급시_해당_이메일의_리프레시_토큰이_없다면_예외를_던진다() {
        // given
        MemberDetails memberDetails = new MemberDetails(existMember);

        given(refreshTokenRepository.findById(memberDetails.getUsername()))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> jwtTokenProvider.reissueAccessToken(memberDetails.getUsername(), memberDetails.getAuthorities().toString()))
                .isInstanceOf(TokenExpiredException.class)
                .hasMessage("존재하지 않는 만료된 refresh 토큰입니다.");
    }

    @Test
    public void 해당_이메일의_리프레시토큰이_있다면_재발급한_억세스토큰을_반환한다() {
        // given
        MemberDetails memberDetails = new MemberDetails(existMember);

        given(jwtTokenProvider.reissueAccessToken(memberDetails.getUsername(), memberDetails.getAuthorities().toString()))
                .willReturn(new TokenResponse("atoken", "rtoken", "b", 1L));

        // when
        TokenResponse tokenResponse = authService.reissueAccessToken(memberDetails);

        // then
        assertThat(tokenResponse.getAccessToken()).isEqualTo("atoken");
        assertThat(tokenResponse.getRefreshToken()).isEqualTo("rtoken");
    }

    @Test
    public void 로그아웃시_해당_이메일의_리프레시토큰이_없다면_예외를_던진다() {
        // given
        String email = existMember.getEmail();
        String refreshToken = "rtoken";

        given(refreshTokenRepository.findById(email))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> authService.logout(email, refreshToken))
                .isInstanceOf(TokenExpiredException.class)
                .hasMessage("이미 로그아웃된 사용자입니다.");
    }

    @Test
    public void 로그아웃시_해당_이메일의_리프레시토큰이_아니라면_예외를_던진다() {
        // given
        String email = existMember.getEmail();
        final String REFRESH_TOKEN = "wrongToken";

        given(refreshTokenRepository.findById(email))
                .willReturn(Optional.of(new RefreshToken("wrong@gmail.com", "rtoken", 1L)));

        // when, then
        assertThatThrownBy(() -> authService.logout(email, REFRESH_TOKEN))
                .isInstanceOf(TokenNotValidException.class)
                .hasMessage("로그인된 사용자의 refresh 토큰이 아닙니다.");
    }

    @Test
    public void 로그아웃시_해당_이메일의_리프레시토큰이라면_삭제한다() {
        // given
        String email = existMember.getEmail();
        String refreshToken = "rtoken";

        given(refreshTokenRepository.findById(email))
                .willReturn(Optional.of(new RefreshToken(existMember.getEmail(), refreshToken, 1L)));

        // when
        authService.logout(email, refreshToken);

        // then
        then(refreshTokenRepository).should(times(1)).delete(any());
    }
}