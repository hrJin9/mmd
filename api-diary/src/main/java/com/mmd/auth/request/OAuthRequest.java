package com.mmd.auth.request;

import com.mmd.domain.OAuthProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OAuthRequest {
    private final String nickname;
    private final String email;
    private final OAuthProvider oAuthProvider;
}
