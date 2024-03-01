package com.mmd.oauth.client.dto;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
public class NaverProviderInfo implements OAuthProviderInfo {
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    public NaverProviderInfo(
            @Value("${oauth2.naver.client-id}") String clientId,
            @Value("${oauth2.naver.client-secret}") String clientSecret,
            @Value("${oauth2.naver.redirect-uri}") String redirectUri
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
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
    public String getRedirectUri() {
        return this.redirectUri;
    }

}
