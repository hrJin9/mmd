package com.mmd.member;

import com.mmd.application.AuthService;
import com.mmd.application.dto.TokenDto;
import com.mmd.member.mapper.ServiceDtoMapper;
import com.mmd.member.request.AuthRequest;
import com.mmd.member.response.TokenResponse;
import com.mmd.security.MemberDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Api(tags = "인증 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "로그인", notes = "일반 회원이 로그인합니다.", tags = "인증 API")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthRequest.LoginRequest request){
        TokenDto tokenDto = authService.login(ServiceDtoMapper.mapping(request));
        return ResponseEntity.ok()
                .body(TokenResponse.from(tokenDto));
    }

    @ApiOperation(value = "억세스 토큰 재발급", notes = "억세스 토큰 만료 시, 토큰을 재발급 받습니다.", tags = "인증 API")
    @GetMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@AuthenticationPrincipal MemberDetails memberDetails) {
        TokenDto tokenDto = authService.reissueAccessToken(memberDetails);
        return ResponseEntity.ok().
                body(TokenResponse.from(tokenDto));
    }

    /* 로그아웃 */
    @ApiOperation(value = "로그아웃", notes = "일반 회원이 로그아웃 합니다.", tags = "인증 API")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            Principal principal,
            @RequestHeader("refreshToken") String refreshToken
    ) {
        authService.logout(principal.getName(), refreshToken);
        return ResponseEntity.noContent().build();
    }
    

}
