package com.mmd.oauth.client.dto;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoProviderInfo implements OAuthProviderInfo {
    @Value("${oauth2.kakao.client-id}")
    private String clientId;

    @Value("${oauth2.kakao.client-secret}")
    private String clientSecret;

    @Value("${oauth2.kakao.end-point-uri}")
    private String endPointUri;

    @Value("${oauth2.kakao.auth-uri}")
    private String authUri;

    @Value("${oauth2.kakao.token-uri}")
    private String tokenUri;

    @Value("${oauth2.kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${oauth2.kakao.grant-type}")
    private String grantType;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getEndPointUrl() {
        return this.endPointUri
                + "?client_id=" + this.getClientId()
                + "&response_type=code"
                + "&redirect_uri=" + this.getAuthUri()  + "/authorize";
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
    public String getAuthUri() {
        return this.authUri;
    }

    @Override
    public String getTokenUri() {
        return this.tokenUri;
    }

    @Override
    public String getUserInfoUri() {
        return this.userInfoUri;
    }

    @Override
    public String getGrantType() {
        return this.grantType;
    }

    @Override
    public String getState() {
        return null;
    }
}
