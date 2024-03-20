package com.mmd.oauth.client.response;

import com.mmd.domain.OAuthProvider;

public interface OAuthUserInfo {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
    String getOAuthId();
}
