package com.kbe5.rento.common.jwt.util;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.common.jwt.dto.JwtManagerArgumentDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private SecretKey secretKey;
    private final RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        this.secretKey = new SecretKeySpec(JwtProperties.JWT_TOKEN.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getRole(String token) {
        return parseClaim(token, "role", String.class);
    }

    public String getCategory(String token) {
        return parseClaim(token, "category", String.class);
    }

    public Long getId(String token) {
        return parseClaim(token, "id", Long.class);
    }

    public String getName(String token) {
        return parseClaim(token, "name", String.class);
    }

    public String getLoginId(String token) {
        return parseClaim(token, "loginId", String.class);
    }

    public String getEmail(String token) {
        return parseClaim(token, "email", String.class);
    }

    public Long getCompanyId(String token) {
        return parseClaim(token, "companyId", Long.class);
    }

    public String getCompanyCode(String token) {
        return parseClaim(token, "companyCode", String.class);
    }

    public Boolean isExpired(String token, HttpServletResponse response) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();

            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private <T> T parseClaim(String token, String claimKey, Class<T> clazz) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get(claimKey, clazz);
        } catch (JwtException | IllegalArgumentException e) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }
    }

    public String createJwt(String category, JwtManagerArgumentDto dto, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("id", dto.id())
                .claim("loginId", dto.loginId())
                .claim("role", dto.role())
                .claim("companyId", dto.companyId())
                .claim("companyCode", dto.companyCode())
                .claim("email", dto.email())
                .claim("name", dto.name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public void getNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refresh = request.getHeader("RefreshToken");
        String category = getCategory(refresh);

        JwtManagerArgumentDto managerArgumentDto = JwtManagerArgumentDto.of(this, refresh);

        if (refresh == null || refresh.isEmpty()) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }

        if (!category.equals("refresh")) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }

        if (isExpired(refresh, response) || !redisTemplate.hasKey(managerArgumentDto.id().toString())) {
            //Todo: 로그아웃 로직 구현
            throw new DomainException(ErrorType.EXPIRED_TOKEN);
        }

        if (!getRefreshFromRedis(managerArgumentDto.id()).equals(refresh)) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }

        String newAccess = createJwt("access", managerArgumentDto, JwtProperties.ACCESS_EXPIRED_TIME);
        String newRefresh = createJwt("refresh", managerArgumentDto, JwtProperties.REFRESH_EXPIRED_TIME);

        saveRefreshTokenToRedis(managerArgumentDto.id(), newRefresh);

        response.setHeader("AccessToken", newAccess);
        response.setHeader("RefreshToken", newRefresh);
    }

    public void saveRefreshTokenToRedis(Long id, String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String key = id.toString();

        valueOperations.set(
                key,
                refreshToken,
                JwtProperties.REFRESH_EXPIRED_TIME,
                TimeUnit.MILLISECONDS
        );
    }

    public String getRefreshFromRedis(Long id) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        return valueOperations.get(id.toString());
    }
}
