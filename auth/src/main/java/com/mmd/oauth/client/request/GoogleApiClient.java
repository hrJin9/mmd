package com.mmd.oauth.client.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmd.domain.OAuthProvider;
import com.mmd.exception.MmdApiException;
import com.mmd.exception.OAuth2Exception;
import com.mmd.oauth.client.dto.GoogleTokens;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import com.mmd.oauth.client.response.GoogleOAuthUserInfo;
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
public class GoogleApiClient implements OAuthApiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public String requestAccessToken(OAuthProviderInfo providerInfo, String authorizationCode) {
        String url = providerInfo.getAuthUri() + "/token";

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("grant_type", providerInfo.getGrantType());
        body.add("client_id", providerInfo.getClientId());
        body.add("client_secret", providerInfo.getClientSecret());
        body.add("redirect_uri", url);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        GoogleTokens response = restTemplate.postForObject(providerInfo.getTokenUri(), request, GoogleTokens.class);
        return response.getAccessToken();
    }

    @Override
    public OAuthUserInfo requestUserInfo(OAuthProviderInfo providerInfo, String accessToken) {
        String uri = providerInfo.getUserInfoUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        String responseBody = response.getBody();

        try {
            return objectMapper.readValue(responseBody, GoogleOAuthUserInfo.class);
        } catch (JsonProcessingException e) {
            throw new OAuth2Exception("GoogleOAuthUserInfo 직렬화 실패");
        }
    }
}
