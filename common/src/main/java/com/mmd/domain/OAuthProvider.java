package com.mmd.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuthProvider {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String name;

    public static OAuthProvider ofName(String name) {
        for(OAuthProvider provider : OAuthProvider.values()) {
            if(provider.name.equalsIgnoreCase(name)) {
                return provider;
            }
        }
        return null;
    }
}
