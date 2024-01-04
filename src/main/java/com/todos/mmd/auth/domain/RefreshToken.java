package com.todos.mmd.auth.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "refreshToken") // key 설정
public class RefreshToken {

    @Id
    private final String email;

    private final String refreshToken;

    @TimeToLive
    private final long expiresIn;

    public RefreshToken(String email, String refreshToken, long expiresIn) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public static RefreshToken of(String email, String refreshToken, long expiresIn) {
        return new RefreshToken(email, refreshToken, expiresIn);
    }

}
