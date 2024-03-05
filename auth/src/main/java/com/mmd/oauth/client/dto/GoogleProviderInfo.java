package com.mmd.oauth.client.dto;

import org.springframework.beans.factory.annotation.Value;

public class GoogleProviderInfo implements OAuthProviderInfo {
    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.redirect-uri}")
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
