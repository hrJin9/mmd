package com.mmd.oauth.application.dto;

import java.util.Map;

public class OAuth2NaverMember extends OAuth2Member {
    public OAuth2NaverMember(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getSocialId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response == null ? null : (String) attributes.get("id");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response == null ? null : (String) attributes.get("name");
    }

    @Override
    public String getNickname() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response == null ? null : (String) attributes.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response == null ? null : (String) attributes.get("email");
    }

}
