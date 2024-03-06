package com.mmd.oauth.client.dto.naver;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.OAuthLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
    private final String authorizationCode;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("authorizationCode", authorizationCode);
        return body;
    }
}
