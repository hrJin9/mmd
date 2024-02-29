package com.mmd.oauth.dto;

import java.util.Map;

public abstract class OAuth2Member {
    protected Map<String, Object> attributes;

    public OAuth2Member(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getSocialId(); // 식별값
    public abstract String getName();
    public abstract String getNickname();
    public abstract String getEmail();

}
