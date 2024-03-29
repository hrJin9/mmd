package com.mmd.oauth.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmd.domain.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleOAuthUserInfo implements OAuthUserInfo {
    @JsonProperty("sub")
    private String id;

    @JsonProperty("given_name")
    private String nickName;

    @JsonProperty("email")
    private String email;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getNickname() {
        return nickName;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public String getOAuthId() {
        return id;
    }
}
