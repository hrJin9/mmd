package com.mmd.oauth.client.dto;

import org.springframework.beans.factory.annotation.Value;

public class KakakoProviderInfo extends OAuthProviderInfo {
    public KakakoProviderInfo(
            @Value("${oauth2.kakao.client-id}") String clientId,
            @Value("${oauth2.kakao.client-secret}") String clientSecret,
            @Value("${oauth2.kakao.redirect-uri}") String redirectUri
    ) {
        super(clientId, clientSecret, redirectUri);
    }
}
