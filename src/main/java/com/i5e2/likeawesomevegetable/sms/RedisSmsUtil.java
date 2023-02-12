package com.i5e2.likeawesomevegetable.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisSmsUtil {

    private final String PREFIX = "sms:";
    private final int LIMIT_TIME = 3 * 60;

    private final StringRedisTemplate stringRedisTemplate;

    public void saveSmsAuth(String phone, String smsCode) {
        stringRedisTemplate.opsForValue().set(PREFIX + phone, smsCode, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getSmsAuth(String phone) {
        return stringRedisTemplate.opsForValue().get(PREFIX + phone);
    }

    public void deleteSmsAuth(String phone) {
        stringRedisTemplate.delete(PREFIX + phone);
    }

    public boolean hasKey(String phone) {
        return stringRedisTemplate.hasKey(PREFIX + phone);
    }
}
