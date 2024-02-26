package com.mmd.oauth2.application.dto;

import com.mmd.domain.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/* 소셜 별로 받는 유저 데이터를 처리하는 DTO */
@Getter
@RequiredArgsConstructor
@Builder
public class OAuthAttributes {
    private final String nameAttributeKey;      // OAuth2 key
    private final OAuth2Member oAuth2Member;    // 소셜별 유저 정보
    
    /* SocialType에 따른 OAuthAttribute 반환 */
    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, // OAuth2 Key
                                     Map<String, Object> attributes // 소셜의 유저 정보
    ) {
        if(socialType.equals(SocialType.NAVER)) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (socialType.equals(SocialType.KAKAO)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        if (socialType.equals(SocialType.GOOGLE)) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        return null;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2Member(new OAuth2NaverMember(attributes))
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2Member(new OAuth2KakaoMember(attributes))
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2Member(new OAuth2GoogleMember(attributes))
                .build();
    }

}
