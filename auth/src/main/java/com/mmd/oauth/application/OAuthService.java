package com.mmd.oauth.application;

import com.mmd.domain.OAuthProvider;
import com.mmd.oauth.client.dto.GoogleProviderInfo;
import com.mmd.oauth.client.dto.KakakoProviderInfo;
import com.mmd.oauth.client.dto.NaverProviderInfo;
import com.mmd.oauth.client.dto.OAuthProviderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {

    /* oauth 서버에 따른 redirect Uri를 알아온다. */
    public String findLoginRedirectUri(String oAuthProvider) {
        OAuthProviderInfo oAuthProviderInfo = getOAuthProviderInfo(oAuthProvider);
        return oAuthProviderInfo.getRedirectUri();
    }

    private OAuthProviderInfo getOAuthProviderInfo(String oAuthProvider) {
        if(oAuthProvider.equalsIgnoreCase(OAuthProvider.GOOGLE.toString())) {
            return new GoogleProviderInfo();
        }
        if (oAuthProvider.equalsIgnoreCase(OAuthProvider.KAKAO.toString())) {
            return new KakakoProviderInfo();
        }
        return new NaverProviderInfo();
    }
}
