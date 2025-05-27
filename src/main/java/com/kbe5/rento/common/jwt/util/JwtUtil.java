package com.kbe5.rento.common.jwt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
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

    public String getLoginId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("loginId",
                String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",
                String.class);
    }

    public String getCategory(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category",
                    String.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }
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

    public String createJwt(String category, String loginId, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("loginId", loginId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public void saveRefreshToken(String refreshToken, Manager manager, Long expiredTime) {
        jwtRefreshRepository.save(JwtRefresh.builder()
                        .manager(manager)
                        .refreshToken(refreshToken)
                        .expiredTime(expiredTime)
                        .build());
    }

    public void getNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refresh = request.getHeader("RefreshToken");

        DomainException domainException = new DomainException(ErrorType.INVALID_TOKEN);

        String category;

        if (refresh == null || refresh.isEmpty()) {
            throw domainException;
        }

        try {
            category = getCategory(refresh);
        } catch (Exception e) {
            throw domainException;
        }

        if (!category.equals("refresh") || !jwtRefreshRepository.existsByRefreshToken(refresh)) {
            throw domainException;
        }

        if (isExpired(refresh)) {
            return;
        }

        String username = getLoginId(refresh);
        String role = getRole(refresh);

        String newAccess = createJwt("access", username, role, JwtProperties.ACCESS_EXPIRED_TIME);
        String newRefresh = createJwt("refresh", username, role, JwtProperties.ACCESS_EXPIRED_TIME);

        response.setHeader("AccessToken", newAccess);
        response.setHeader("RefreshToken", newRefresh);
    }

    public void tokenErrorResponse(HttpServletResponse response, ErrorType errorType) throws IOException {
        DomainException domainException = new DomainException(errorType);
        response.setContentType("application/json;charset=UTF-8");

        response.setStatus(domainException.getStatus().value());

        String body = new ObjectMapper().writeValueAsString(domainException.toResponse());

        response.getWriter().write(body);
    }
}