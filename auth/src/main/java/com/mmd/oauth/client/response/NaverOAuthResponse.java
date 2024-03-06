package com.mmd.oauth.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.response.OAuthResponse;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverOAuthResponse implements OAuthResponse {
    @JsonProperty("response")
    private NaverAccount naverAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class NaverAccount {
        private Long id;
        private String email;
        private String nickname;
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
    public Long getOAuthId() {
        return naverAccount.id;
    }
}
