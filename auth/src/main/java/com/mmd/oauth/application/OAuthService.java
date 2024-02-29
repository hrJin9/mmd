package com.mmd.oauth.application;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.KakakoProviderInfo;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {


    public String findLoginRedirectUri(String oAuthProvider) {
        OAuthProviderInfo oAuthProviderInfo = getOAuthProviderInfo(oAuthProvider);
    }

    private OAuthProviderInfo getOAuthProviderInfo(String oAuthProvider) {
        if(oAuthProvider.equals(OAuthProvider.GOOGLE)) {
            return new KakakoProviderInfo();
        }

    }
}
