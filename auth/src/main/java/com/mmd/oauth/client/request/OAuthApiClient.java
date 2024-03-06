package com.mmd.oauth.client.request;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.OAuthLoginParams;
import com.mmd.oauth.client.response.OAuthResponse;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);
    OAuthResponse requestOAuthResponse(String accessToken);

}
