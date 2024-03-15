package com.mmd.oauth.client.request;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.KakaoTokens;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import com.mmd.oauth.client.response.OAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class KakaoApiClient implements OAuthApiClient {
    private static final String GRANT_TYPE = "authorization_code";
//    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthProviderInfo providerInfo, String authorizationCode) {
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("authorization_code", authorizationCode);
//        body.add("grant_type", GRANT_TYPE);
//        body.add("client_id", providerInfo.getClientId());
//        body.add("redirect_uri", providerInfo.getRedirectUri());
//
//        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
//        KakaoTokens response = restTemplate.postForObject()
        return null;
    }

    @Override
    public OAuthResponse requestOAuthResponse(String accessToken) {
        return null;
    }
}
