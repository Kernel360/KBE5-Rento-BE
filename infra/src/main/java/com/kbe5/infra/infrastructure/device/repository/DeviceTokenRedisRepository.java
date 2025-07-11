package com.kbe5.infra.infrastructure.device.repository;

import com.kbe5.domain.device.entity.DeviceToken;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeviceTokenRedisRepository implements DeviceTokenRepository {

    private final RedisTemplate<String, DeviceToken> redisTemplate;

    public DeviceToken save(DeviceToken deviceToken) {
        long ttl = deviceToken.getExPeriod() / 1000;
        String key = "deviceToken:" + deviceToken.getToken();
        redisTemplate.opsForValue().set(key, deviceToken, ttl, TimeUnit.SECONDS);
        return redisTemplate.opsForValue().get(key);
    }

    public Optional<DeviceToken> findById(String token) {
        String key = "deviceToken:" + token;
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public void deleteById(String token) {
        String key = "deviceToken:" + token;
        redisTemplate.delete(key);
    }
}
