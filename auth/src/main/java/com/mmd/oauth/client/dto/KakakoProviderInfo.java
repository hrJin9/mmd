package com.mmd.oauth.client.dto;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
public class KakakoProviderInfo implements OAuthProviderInfo {
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    public KakakoProviderInfo(
            @Value("${oauth2.kakao.client-id}") String clientId,
            @Value("${oauth2.kakao.client-secret}") String clientSecret,
            @Value("${oauth2.kakao.redirect-uri}") String redirectUri
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
