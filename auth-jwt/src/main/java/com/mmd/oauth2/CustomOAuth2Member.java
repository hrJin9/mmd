package com.mmd.oauth2;

import com.mmd.domain.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

/* 소셜(OAuth2 제공 리소스 서버)에서 제공하지 않는 추가 정보 */
@Getter
public class CustomOAuth2Member extends DefaultOAuth2User {
    private final MemberRole role;
    private final String email;

    public CustomOAuth2Member(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String email, MemberRole role) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
    }

}
