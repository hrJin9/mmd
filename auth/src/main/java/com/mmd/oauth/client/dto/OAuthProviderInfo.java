package com.mmd.oauth.client.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class OAuthProviderInfo {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
