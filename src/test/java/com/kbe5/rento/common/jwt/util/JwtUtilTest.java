package com.kbe5.rento.common.jwt.util;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.jwt.entity.JwtRefresh;
import com.kbe5.rento.common.jwt.respository.JwtRefreshRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private JwtRefreshRepository jwtRefreshRepository;

    @Mock
    private DomainException domainException;

    private final SecretKey secretKey = new SecretKeySpec(
            JwtProperties.JWT_TOKEN.getBytes(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().getAlgorithm()
    );

    private String accessToken;
    private String refreshToken;
    private String expiredToken;

    @BeforeEach
    void init() {
        accessToken = jwtUtil.createJwt("access", "test", "ROLE_MANAGER", 60000L);
        refreshToken = jwtUtil.createJwt("refresh", "test", "ROLE_MANAGER", 60000L);
        expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6InJlZnJlc2giLCJsb2dpbklkIjoidGVzdDMiLCJyb2xlIjoiUk9MRV9N" +
                "QU5BR0VSIiwiaWF0IjoxNzQ4MzEzOTE5LCJleHAiOjE3NDgzMTM5NDl9.8C1b58R8_QdmGpH20e5veUHOybLTJDJJdAkur7p33FQ";
    }

    @Test
    void getLoginId() {
        String loginId = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken).getPayload().get(
                "loginId", String.class);

        assertThat(loginId).isEqualTo("test");
    }

    @Test
    void getRole() {
        String role = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken).getPayload().get("role",
                String.class);

        assertThat(role).isEqualTo("ROLE_MANAGER");
    }

    @Test
    void getCategory() {
        String access = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken).getPayload().get(
                "category",
                String.class);

        String refresh = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken).getPayload().get(
                "category",
                String.class);

        assertThat(access).isEqualTo("access");
        assertThat(refresh).isEqualTo("refresh");
    }

    @Test
    void isExpired() {
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(expiredToken)
                .getPayload()
                .getExpiration();

        assertThat(expiration.before(new Date())).isTrue();
    }

    @Test
    void createJwt() {
        // given
        String category = "access";
        String loginId = "test";
        String role = "ROLE_MANAGER";
        long expiryMs = 60000L; // 1분

        // when
        String token = jwtUtil.createJwt(category, loginId, role, expiryMs);

        // then
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token.replace("Bearer ", ""))
                .getPayload();

        assertThat(claims.get("category")).isEqualTo(category);
        assertThat(claims.get("loginId")).isEqualTo(loginId);
        assertThat(claims.get("role")).isEqualTo(role);
        assertThat(claims.getExpiration()).isAfter(new Date());
    }

    @Test
    void saveRefreshToken() {
        // given
        Manager manager = Manager.builder().build();
        Long expiredTime = 60000L;

        // when
        jwtUtil.saveRefreshToken(refreshToken, manager, expiredTime);

        // then
        ArgumentCaptor<JwtRefresh> captor = ArgumentCaptor.forClass(JwtRefresh.class);
        verify(jwtRefreshRepository).save(captor.capture());

        JwtRefresh saved = captor.getValue();
        assertThat(saved.getManager()).isEqualTo(manager);
        assertThat(saved.getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void getNewAccessToken() {
        // given
        String refreshToken = jwtUtil.createJwt("refresh", "testUser", "ROLE_MANAGER", 60000L);
        HttpServletRequest request = mock(HttpServletRequest.class);
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(request.getHeader("RefreshToken")).thenReturn(refreshToken);
        when(jwtRefreshRepository.existsByRefreshToken(refreshToken)).thenReturn(true);

        // when
        jwtUtil.getNewAccessToken(request, response);

        // then
        String newAccessToken = response.getHeader("AccessToken");
        assertThat(newAccessToken).isNotNull();
    }
}