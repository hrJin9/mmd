package com.todos.mmd.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> values = redisTemplate.opsForValue();

    public void setValues(String key, String data) {
        values.set(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public boolean isExists(String key) {
        return StringUtils.hasText(values.get(key));
    }

    public boolean Matches(String key, String value) {
        String tokenInRedis = values.get(key);
        return value.matches(tokenInRedis);
    }
}
