package com.kbe5.api.domain.jwt.util;

import com.kbe5.api.domain.jwt.dto.JwtManagerArgumentDto;
import com.kbe5.api.domain.jwt.dto.request.PayloadDecryptRequest;
import com.kbe5.api.domain.jwt.dto.response.PayloadDecryptResponse;
import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import com.kbe5.common.util.Aes256Util;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${JWT_KEY}")
    private String jwtKey;
    private SecretKey secretKey;
    private final RedisTemplate<String, String> redisTemplate;
    private final Aes256Util aes256Util;

    @PostConstruct
    public void init() {
        this.secretKey = new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getRole(String token) {
        return parseClaim(token, "role", String.class);
    }

    public String getCategory(String token) {
        return parseClaim(token, "category", String.class);
    }

    public String getUuid(String token) {
        return parseClaim(token, "uuid", String.class);
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

    public Boolean isExpired(String token) {
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
                .claim("uuid", dto.uuid())
                .claim("loginId", aes256Util.AES_Encode(dto.loginId()))
                .claim("role", dto.role())
                .claim("companyId", dto.companyId())
                .claim("companyCode", dto.companyCode())
                .claim("email", aes256Util.AES_Encode(dto.email()))
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

        if (isExpired(refresh) || !redisTemplate.hasKey(managerArgumentDto.uuid())) {
            throw new DomainException(ErrorType.EXPIRED_TOKEN_REFRESH);
        }

        if (!getRefreshFromRedis(managerArgumentDto.uuid()).equals(refresh)) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }

        String newAccess = createJwt("access", managerArgumentDto, JwtProperties.ACCESS_EXPIRED_TIME);
        String newRefresh = createJwt("refresh", managerArgumentDto, JwtProperties.REFRESH_EXPIRED_TIME);

        saveRefreshTokenToRedis(managerArgumentDto.uuid(), newRefresh);

        response.setHeader("AccessToken", newAccess);
        response.setHeader("RefreshToken", newRefresh);
    }

    public void saveRefreshTokenToRedis(String uuid, String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.set(
                uuid,
                refreshToken,
                JwtProperties.REFRESH_EXPIRED_TIME,
                TimeUnit.MILLISECONDS
        );
    }

    public String getRefreshFromRedis(String uuid) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        return valueOperations.get(uuid);
    }

    public PayloadDecryptResponse getDecryptData(PayloadDecryptRequest request) {
        return new PayloadDecryptResponse(aes256Util.AES_Decode(request.claim()));
    }
}
