package com.mmd.oauth.client.request;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import com.mmd.oauth.client.response.OAuthResponse;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthProviderInfo oAuthProviderInfo, String authorizationCode);
    OAuthResponse requestOAuthResponse(String accessToken);

}
