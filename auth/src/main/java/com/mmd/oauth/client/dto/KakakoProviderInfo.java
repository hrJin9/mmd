package com.mmd.oauth.client.dto;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

public class KakakoProviderInfo implements OAuthProviderInfo {
    @Value("${oauth2.kakao.client-id}")
    private String clientId;

    @Value("${oauth2.kakao.client-secret}")
    private String clientSecret;

    @Value("${oauth2.kakao.redirect-uri}")
    private String redirectUri;

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
