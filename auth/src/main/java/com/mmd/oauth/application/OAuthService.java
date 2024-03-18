package com.mmd.oauth.application;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.*;
import com.mmd.oauth.client.request.OAuthApiClient;
import com.mmd.oauth.client.response.OAuthResponse;
import com.mmd.repository.MemberRepository;
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
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public OAuthService(MemberRepository memberRepository,
                        List<OAuthProviderInfo> providers,
                        List<OAuthApiClient> clients) {
        this.memberRepository = memberRepository;
        this.providers = providers.stream().collect(Collectors.toUnmodifiableMap(OAuthProviderInfo::oAuthProvider, Function.identity()));
        this.clients = clients.stream().collect(Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity()));
    }

    /* oauth 서버에 따른 redirect uri(auth)를 알아온다. */
    public String findLoginRedirectUri(OAuthProvider oAuthProvider) {
        OAuthProviderInfo oAuthProviderInfo = providers.get(oAuthProvider);

        return oAuthProviderInfo.getEndPointUri()
                + "?client_id=" + oAuthProviderInfo.getClientId()
                + "&response_type=code"
                + "&redirect_uri=" + oAuthProviderInfo.getAuthUri();
    }

    /* OAuth 로그인한다. */
    public OAuthResponse login(OAuthProvider oAuthProvider, String authorizationCode) {
        OAuthApiClient client = clients.get(oAuthProvider);
        OAuthProviderInfo providerInfo = providers.get(oAuthProvider);
        String accessToken = client.requestAccessToken(providerInfo, authorizationCode);
        return client.requestOAuthResponse(accessToken);
    }

}
