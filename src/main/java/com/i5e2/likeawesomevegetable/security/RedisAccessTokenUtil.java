package com.i5e2.likeawesomevegetable.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisAccessTokenUtil {

    private final String PREFIX = "accesstoken:";
    private final String BLOCK_PREFIX = "block:";

    private final int LIMIT_TIME = 60 * 60; // 1h
    private final StringRedisTemplate stringRedisTemplate;

    public void saveAccessToken(String email, String jwt) {
        stringRedisTemplate.opsForValue().set(PREFIX + email, jwt, Duration.ofSeconds(LIMIT_TIME));
    }

    public void saveBlockAccessToken(String jwt) {
        stringRedisTemplate.opsForValue().set(BLOCK_PREFIX + jwt, "block", Duration.ofSeconds(LIMIT_TIME));
    }

    public String getAccessToken(String email) {
        return stringRedisTemplate.opsForValue().get(PREFIX + email);
    }

    public void deleteAccessToken(String email) {
        stringRedisTemplate.delete(PREFIX + email);
    }

    public boolean hasAccessToken(String email) {
        return stringRedisTemplate.hasKey(PREFIX + email);
    }

    public boolean hasBlockAccessToken(String jwt) {
        return stringRedisTemplate.hasKey(BLOCK_PREFIX + jwt);
    }
}
