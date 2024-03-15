package com.mmd.oauth.client.dto;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoogleProviderInfo implements OAuthProviderInfo {
    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.auth-uri}")
    private String authUri;

    @Value("${oauth2.google.token-uri}")
    private String tokenUri;

    @Value("${oauth2.google.user-info-uri}")
    private String userInfoUri;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GOOGLE;
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
