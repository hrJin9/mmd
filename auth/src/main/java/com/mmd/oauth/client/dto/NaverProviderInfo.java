package com.mmd.oauth.client.dto;

import org.springframework.beans.factory.annotation.Value;

public class NaverProviderInfo extends OAuthProviderInfo {

    public NaverProviderInfo(
            @Value("${oauth2.naver.client-id}") String clientId,
            @Value("${oauth2.naver.client-secret}") String clientSecret,
            @Value("${oauth2.naver.redirect-uri}") String redirectUri
    ) {
        super(clientId, clientSecret, redirectUri);
    }

}
