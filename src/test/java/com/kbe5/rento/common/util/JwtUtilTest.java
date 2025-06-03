package com.kbe5.rento.common.util;

import com.kbe5.rento.common.jwt.dto.JwtManagerArgumentDto;
import com.kbe5.rento.common.jwt.entity.JwtRefresh;
import com.kbe5.rento.common.jwt.respository.JwtRefreshRepository;
import com.kbe5.rento.common.jwt.util.JwtProperties;
import com.kbe5.rento.common.jwt.util.JwtUtil;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.enums.ManagerRole;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private JwtRefreshRepository jwtRefreshRepository;

    @Mock
    private ManagerRepository managerRepository;

    private final SecretKey secretKey = new SecretKeySpec(
            JwtProperties.JWT_TOKEN.getBytes(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().getAlgorithm()
    );

    private String accessToken;
    private String refreshToken;
    private String expiredToken;

    private Manager manager;
    private JwtManagerArgumentDto jwtManagerArgumentDto;

    @BeforeEach
    @DisplayName("테스트를 위한 초기 설정")
    void init() {
        Company company = Company.builder()
                .name("testCompany")
                .bizNumber(12312414)
                .build();

        manager = Manager.builder()
                .loginId("testId")
                .password("1234")
                .company(company)
                .phone("010-1234-1234")
                .email("test@test.com")
                .companyCode("C1")
                .name("testName")
                .role(ManagerRole.ROLE_MANAGER)
                .build();

        when(managerRepository.save(any(Manager.class))).thenReturn(manager);

        manager = managerRepository.save(manager);

        jwtManagerArgumentDto = new JwtManagerArgumentDto(1L, manager.getLoginId(), 1L, manager.getName(),
                manager.getPhone(), manager.getEmail(), manager.getCompanyCode(), manager.getRole().toString());

        accessToken = jwtUtil.createJwt("access", jwtManagerArgumentDto, 60000L);
        refreshToken = jwtUtil.createJwt("refresh", jwtManagerArgumentDto, 60000L);
        expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6InJlZnJlc2giLCJsb2dpbklkIjoidGVzdDMiLCJyb2xlIjoiUk9MRV9N" +
                "QU5BR0VSIiwiaWF0IjoxNzQ4MzEzOTE5LCJleHAiOjE3NDgzMTM5NDl9.8C1b58R8_QdmGpH20e5veUHOybLTJDJJdAkur7p33FQ";
    }

    @Test
    @DisplayName("토큰에서 로그인 아이디를 가져오는 로직 테스트")
    void getLoginId() {
        String loginId = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken).getPayload().get(
                "loginId", String.class);

        assertThat(loginId).isEqualTo("testId");
    }

    @Test
    @DisplayName("토큰에서 유저 권한을 가져오는 로직 테스트")
    void getRole() {
        String role = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken).getPayload().get("role",
                String.class);

        assertThat(role).isEqualTo("ROLE_MANAGER");
    }

    @Test
    @DisplayName("토큰에서 카테고리를 가져오는 로직 테스트")
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
    @DisplayName("토큰이 만료돠었는지 확인하는 테스트")
    void isExpired() {
        assertThatThrownBy(() -> {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(expiredToken);
        }).isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    @DisplayName("토큰생성 테스트")
    void createJwt() {
        // given
        String category = "access";
        String loginId = "testId";
        String role = "ROLE_MANAGER";
        long expiryMs = 60000L; // 1분

        // when
        String token = jwtUtil.createJwt(category, jwtManagerArgumentDto, expiryMs);

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
    @DisplayName("refresh 토큰을 저장하는 테스트")
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
    @DisplayName("refresh 토큰으로 새로운 토큰을 발급받는 테스트")
    void getNewAccessToken() {
        // given
        String refreshToken = jwtUtil.createJwt("refresh", jwtManagerArgumentDto, 60000L);
        HttpServletRequest request = mock(HttpServletRequest.class);
        MockHttpServletResponse response = new MockHttpServletResponse();

        JwtRefresh jwtRefresh = JwtRefresh.builder()
                .refreshToken(refreshToken)
                .manager(manager)
                .expiredTime(JwtProperties.REFRESH_EXPIRED_TIME)
                .build();

        when(request.getHeader("RefreshToken")).thenReturn(refreshToken);
        when(jwtRefreshRepository.existsByRefreshToken(refreshToken)).thenReturn(true);
        when(jwtRefreshRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.ofNullable(jwtRefresh));
        // when
        jwtUtil.getNewAccessToken(request, response);

        // then
        String newAccessToken = response.getHeader("AccessToken");
        assertThat(newAccessToken).isNotNull();
    }
}