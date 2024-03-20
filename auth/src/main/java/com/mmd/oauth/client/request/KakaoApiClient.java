package com.mmd.oauth.client.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmd.domain.OAuthProvider;
import com.mmd.exception.OAuth2Exception;
import com.mmd.oauth.client.dto.KakaoTokens;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import com.mmd.oauth.client.response.KakaoOAuthUserInfo;
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
public class KakaoApiClient implements OAuthApiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

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
        KakaoTokens response = restTemplate.postForObject(providerInfo.getTokenUri(), request, KakaoTokens.class);
        return response.getAccessToken();
    }

    @Override
    public OAuthUserInfo requestUserInfo(OAuthProviderInfo providerInfo, String accessToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(body, header);

        ResponseEntity<String> response = restTemplate.postForEntity(providerInfo.getUserInfoUri(), request, String.class);
        String responseBody = response.getBody();

        try {
            return objectMapper.readValue(responseBody, KakaoOAuthUserInfo.class);
        } catch (JsonProcessingException e) {
            throw new OAuth2Exception("Kakao userinfo 직렬화 실패");
        }
    }
}
