package com.mmd.oauth.client.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmd.domain.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverOAuthUserInfo implements OAuthUserInfo {
    @JsonProperty("response")
    private NaverAccount naverAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class NaverAccount {
        private String id;
        private String email;
        private String nickname;
        private String mobile;
    }

    @Override
    public String getEmail() {
        return naverAccount.email;
    }

    @Override
    public String getNickname() {
        return naverAccount.nickname;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String getOAuthId() {
        return naverAccount.id;
    }
}
