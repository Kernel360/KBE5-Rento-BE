package com.kbe5.api.domain.jwt.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe5.rento.common.exception.DeviceException;
import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.device.service.DeviceService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceTokenFilter extends OncePerRequestFilter {

    private static final String X_DEVICE_TOKEN = "X-Device-Token";

    private final DeviceService deviceService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();
        return (!path.startsWith("/api/devices") && !path.startsWith("/api/events")); // 이 경로만 필터 작동
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String token = request.getHeader(X_DEVICE_TOKEN);

        if (token == null || token.isBlank()) {
            // 디바이스 토큰이 없으면 → 이 필터는 인증 안 함
            filterChain.doFilter(request, response);
            return;
        }

        log.info("🥹 디바이스 필터에 걸리는 로직 ");

        try {
            DeviceToken deviceToken = deviceService.validateAndGetToken(token);
            Authentication auth = new DeviceAuthenticationToken(deviceToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response); // 인증 성공 시 계속 진행
        } catch (DeviceException e) {
            deviceErrorResponse(response, e);
        }
    }

    // 예외 응답 메서드
    public void deviceErrorResponse(HttpServletResponse response, DeviceException deviceException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value()); // 연동 규격서 항상 200으로 전송
        String body = new ObjectMapper().writeValueAsString(deviceException.toResponse());
        response.getWriter().write(body);
    }
}
