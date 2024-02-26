package com.mmd.oauth2.application.dto;

import java.util.Map;

public class OAuth2KakaoMember extends OAuth2Member {
    public OAuth2KakaoMember(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getSocialId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        return account == null ? null : (String) attributes.get("name");
    }

    @Override
    public String getNickname() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        if(account == null) {
            return null;
        }
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        return profile == null ? null : (String) profile.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        return account == null ? null :  (String) account.get("email");
    }

}
