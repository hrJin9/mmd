package com.mmd.oauth2.application.dto;

import java.util.Map;

public class OAuth2GoogleMember extends OAuth2Member {
    public OAuth2GoogleMember(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getSocialId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("given_name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

}
