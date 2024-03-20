package com.mmd.oauth.client.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmd.domain.OAuthProvider;
import com.mmd.exception.OAuth2Exception;
import com.mmd.oauth.client.dto.NaverTokens;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import com.mmd.oauth.client.response.NaverOAuthUserInfo;
import com.mmd.oauth.client.response.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class NaverApiClient implements OAuthApiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
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
        body.add("state", providerInfo.getState());

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        NaverTokens response = restTemplate.postForObject(providerInfo.getTokenUri(), request, NaverTokens.class);
        return response.getAccessToken();
    }

    @Override
    public OAuthUserInfo requestUserInfo(OAuthProviderInfo providerInfo, String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(null, header);
        ResponseEntity<String> response = restTemplate.postForEntity(providerInfo.getUserInfoUri(), request, String.class);
        String responseBody = response.getBody();

        try {
            return objectMapper.readValue(responseBody, NaverOAuthUserInfo.class);
        } catch (JsonProcessingException e) {
            throw new OAuth2Exception("naver oauth2 직렬화 실패");
        }
    }
}
