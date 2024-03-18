package com.mmd.oauth.client.dto;


import com.mmd.domain.OAuthProvider;

public interface OAuthProviderInfo {
    OAuthProvider oAuthProvider();
    String getClientId();
    String getClientSecret();
    String getEndPointUrl();
    String getAuthUri();
    String getTokenUri();
    String getUserInfoUri();
    String getGrantType();
    String getState();
}
