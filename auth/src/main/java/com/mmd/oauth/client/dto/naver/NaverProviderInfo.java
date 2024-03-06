package com.mmd.oauth.client.dto.naver;

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

    @Value("${oauth2.naver.redirect-uri}")
    private String redirectUri;

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
    public String getRedirectUri() {
        return this.redirectUri;
    }

}
