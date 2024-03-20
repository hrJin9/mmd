package com.mmd.oauth.application;

import com.mmd.application.dto.TokenDto;
import com.mmd.domain.OAuthProvider;
import com.mmd.entity.Member;
import com.mmd.oauth.client.dto.*;
import com.mmd.oauth.client.request.OAuthApiClient;
import com.mmd.oauth.client.response.OAuthUserInfo;
import com.mmd.repository.MemberRepository;
import com.mmd.security.jwt.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final Map<OAuthProvider, OAuthProviderInfo> providers;
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public OAuthService(MemberRepository memberRepository,
                        JwtTokenProvider jwtTokenProvider,
                        List<OAuthProviderInfo> providers,
                        List<OAuthApiClient> clients) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.providers = providers.stream().collect(Collectors.toUnmodifiableMap(OAuthProviderInfo::oAuthProvider, Function.identity()));
        this.clients = clients.stream().collect(Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity()));
    }

    /* oauth 서버에 따른 redirect uri(auth)를 알아온다. */
    public String findLoginRedirectUri(OAuthProvider oAuthProvider) {
        OAuthProviderInfo oAuthProviderInfo = providers.get(oAuthProvider);
        return oAuthProviderInfo.getEndPointUrl();
    }

    /* OAuth 로그인한다. */
    public TokenDto login(OAuthProvider oAuthProvider, String authorizationCode) {
        OAuthApiClient client = clients.get(oAuthProvider);
        OAuthProviderInfo providerInfo = providers.get(oAuthProvider);
        String accessToken = client.requestAccessToken(providerInfo, authorizationCode);
        OAuthUserInfo oAuthUserInfo = client.requestUserInfo(providerInfo, accessToken);

        // kakao의 경우 비즈니스 인증이 되어야 email을 가져올 수 있도록 변경되었다.
        // email이 없을 경우 회원가입 처리
        Member member = memberRepository.findByEmail(oAuthUserInfo.getEmail())
                .orElse(memberRepository.save(Member.registerByOauth(oAuthUserInfo.getEmail(),
                                                                        oAuthUserInfo.getNickname(),
                                                                        oAuthUserInfo.getOAuthProvider(),
                                                                        oAuthUserInfo.getOAuthId())));

        // jwt 토큰을 발급한다.
        return jwtTokenProvider.generate(member.getEmail(), member.getRole().toString());
    }

}
