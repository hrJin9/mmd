package com.mmd.oauth.client.response;

import com.mmd.domain.OAuthProvider;

public interface OAuthResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
    Long getOAuthId();
}
