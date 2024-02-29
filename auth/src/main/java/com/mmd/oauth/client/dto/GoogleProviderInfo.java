package com.mmd.oauth.client.dto;

import org.springframework.beans.factory.annotation.Value;

public class GoogleProviderInfo extends OAuthProviderInfo {
    public GoogleProviderInfo(
            @Value("${oauth2.google.client-id}") String clientId,
            @Value("${oauth2.google.client-secret}") String clientSecret,
            @Value("${oauth2.google.redirect-uri}") String redirectUri
    ) {
        super(clientId, clientSecret, redirectUri);
    }
}
