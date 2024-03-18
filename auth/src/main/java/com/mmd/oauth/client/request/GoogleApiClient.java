package com.mmd.oauth.client.request;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.GoogleTokens;
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
public class GoogleApiClient implements OAuthApiClient {
    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthProviderInfo providerInfo, String authorizationCode) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("grant_type", providerInfo.getGrantType());
        body.add("client_id", providerInfo.getClientId());
        body.add("client_secret", providerInfo.getClientSecret());
        body.add("redirect_uri", providerInfo.getAuthUri());

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        GoogleTokens response = restTemplate.postForObject(providerInfo.getTokenUri(), request, GoogleTokens.class);
        return response.getAccessToken();
    }

    @Override
    public OAuthResponse requestOAuthResponse(String accessToken) {
        return null;
    }
}
