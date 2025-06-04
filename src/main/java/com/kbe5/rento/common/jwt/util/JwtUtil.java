package com.kbe5.rento.common.jwt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.common.jwt.dto.JwtManagerArgumentDto;
import com.kbe5.rento.common.jwt.entity.JwtRefresh;
import com.kbe5.rento.common.jwt.respository.JwtRefreshRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final JwtRefreshRepository jwtRefreshRepository;

    public JwtUtil(JwtRefreshRepository jwtRefreshRepository) {
        this.jwtRefreshRepository = jwtRefreshRepository;
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

    public Boolean isExpired(String token) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();

            return expiration.before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
           throw new DomainException(ErrorType.EXPIRED_TOKEN);
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

        if (refresh == null || refresh.isEmpty()) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }

        if (!category.equals("refresh") || !jwtRefreshRepository.existsByRefreshToken(refresh)) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }

        if (isExpired(refresh)) {
            return;
        }

        JwtManagerArgumentDto managerArgumentDto = JwtManagerArgumentDto.of(this, refresh);

        String newAccess = createJwt("access", managerArgumentDto, JwtProperties.ACCESS_EXPIRED_TIME);
        String newRefresh = createJwt("refresh", managerArgumentDto, JwtProperties.REFRESH_EXPIRED_TIME);

        JwtRefresh oldRefreshToken = jwtRefreshRepository.findByRefreshToken(refresh)
                .orElseThrow(() -> new DomainException(ErrorType.REFRESH_TOKEN_NOT_FOUND));

        deleteRefreshToken(oldRefreshToken);

        saveRefreshToken(newRefresh, oldRefreshToken.getManager(), JwtProperties.REFRESH_EXPIRED_TIME);

        response.setHeader("AccessToken", newAccess);
        response.setHeader("RefreshToken", newRefresh);
    }

    public void saveRefreshToken(String refreshToken, Manager manager, Long expiredTime) {
        if (jwtRefreshRepository.existsByRefreshToken(refreshToken)) {
            JwtRefresh jwtRefresh = jwtRefreshRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new DomainException(ErrorType.REFRESH_TOKEN_NOT_FOUND));
            deleteRefreshToken(jwtRefresh);
        };

        jwtRefreshRepository.save(JwtRefresh.builder()
                .manager(manager)
                .refreshToken(refreshToken)
                .expiredTime(expiredTime)
                .build());
    }

    public void deleteRefreshToken(JwtRefresh oldRefreshToken) {
        jwtRefreshRepository.delete(oldRefreshToken);
    }

    public void tokenErrorResponse(HttpServletResponse response, DomainException domainException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        response.setStatus(domainException.getStatus().value());

        String body = new ObjectMapper().writeValueAsString(domainException.toResponse());

        response.getWriter().write(body);
    }
}
