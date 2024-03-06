package com.mmd.oauth.application;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.*;
import com.mmd.oauth.client.dto.google.GoogleLoginParams;
import com.mmd.oauth.client.dto.google.GoogleProviderInfo;
import com.mmd.oauth.client.dto.kakao.KakaoProviderInfo;
import com.mmd.oauth.client.dto.naver.NaverLoginParams;
import com.mmd.oauth.client.dto.naver.NaverProviderInfo;
import com.mmd.oauth.client.request.OAuthApiClient;
import com.mmd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OAuthService {
    private final MemberRepository memberRepository;
    private final Map<OAuthProvider, OAuthProviderInfo> providers;
    private final Map<OAuthProvider, OAuthLoginParams> params;

    public OAuthService(MemberRepository memberRepository,
                        List<OAuthProviderInfo> providers,
                        List<OAuthLoginParams> params) {
        this.memberRepository = memberRepository;
        this.providers = providers.stream().collect(Collectors.toUnmodifiableMap(OAuthProviderInfo::oAuthProvider, Function.identity()));
        this.params = params.stream().collect(Collectors.toUnmodifiableMap(OAuthLoginParams::oAuthProvider, Function.identity()));
    }

    /* oauth 서버에 따른 redirect Uri를 알아온다. */
    public String findLoginRedirectUri(OAuthProvider oAuthProvider) {
        OAuthProviderInfo oAuthProviderInfo = providers.get(oAuthProvider);
        return oAuthProviderInfo.getRedirectUri();
    }

    /* OAuth 로그인한다. */
    public String login(OAuthProvider oAuthProvider, String authorizationCode) {
        OAuthLoginParams oAuthLoginParams = params.get(oAuthProvider);
        return null;
    }

}
