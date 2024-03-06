package com.mmd.oauth.client.request;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.OAuthLoginParams;
import com.mmd.oauth.client.response.OAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Override
    public OAuthProvider oAuthProvider() {
        return null;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        return null;
    }

    @Override
    public OAuthResponse requestOAuthResponse(String accessToken) {
        return null;
    }
}
