package com.kbe5.pub.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.kbe5.domain.device.entity.DeviceToken;
import java.util.concurrent.TimeUnit;

public class CacheManager {
    private static final Cache<String, DeviceToken> tokenCache = Caffeine.newBuilder()
        .expireAfterWrite(10, TimeUnit.MINUTES) // 10분 TTL
        .maximumSize(20_000)                    // 최대 20,000개
        .build();

    public static Cache<String, DeviceToken> getTokenCache() {
        return tokenCache;
    }

}
