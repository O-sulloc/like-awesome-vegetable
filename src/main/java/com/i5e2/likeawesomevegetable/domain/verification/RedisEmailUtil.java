package com.i5e2.likeawesomevegetable.domain.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisEmailUtil {
    private final StringRedisTemplate stringRedisTemplate;

    public boolean hasKey(String email) {
        return stringRedisTemplate.hasKey(email);
    }

    public String getEmailCode(String email) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(email);
    }

    // 유효 시간 동안(key, value)저장
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }
}
