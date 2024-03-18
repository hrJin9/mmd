package com.mmd.oauth.client.dto;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NaverProviderInfo implements OAuthProviderInfo {
    @Value("${oauth2.naver.client-id}")
    private String clientId;

    @Value("${oauth2.naver.client-secret}")
    private String clientSecret;

    @Value("${oauth2.naver.end-point-uri}")
    private String endPointUri;

    @Value("${oauth2.naver.auth-uri}")
    private String authUri;

    @Value("${oauth2.naver.token-uri}")
    private String tokenUri;

    @Value("${oauth2.naver.user-info-uri}")
    private String userInfoUri;
    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public String getEndPointUri() {
        return this.endPointUri;
    }

    @Override
    public String getAuthUri() {
        return this.authUri;
    }

    @Override
    public String getTokenUri() {
        return this.tokenUri;
    }

    @Override
    public String getUserInfoUri() {
        return this.userInfoUri;
    }
}
