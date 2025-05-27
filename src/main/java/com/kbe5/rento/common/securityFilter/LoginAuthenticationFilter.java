package com.kbe5.rento.common.securityFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.common.jwt.util.JwtProperties;
import com.kbe5.rento.common.jwt.util.JwtUtil;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.dto.request.ManagerLoginRequest;
import com.kbe5.rento.domain.manager.dto.response.ManagerLoginResponse;
import com.kbe5.rento.domain.manager.entity.Manager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/api/managers/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            ManagerLoginRequest loginRequest = mapper.readValue(request.getInputStream(), ManagerLoginRequest.class);

            String loginId = loginRequest.loginId();
            String password = loginRequest.password();

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginId, password, null);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse login request"); // 에러타입 수정 후 적용 예정
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomManagerDetails customManagerDetails = (CustomManagerDetails) authResult.getPrincipal();

        Manager manager = customManagerDetails.getManager();

        String loginId = customManagerDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        String accessToken = jwtUtil.createJwt("access", loginId, role, JwtProperties.ACCESS_EXPIRED_TIME);
        String refreshToken = jwtUtil.createJwt("refresh", loginId, role, JwtProperties.REFRESH_EXPIRED_TIME);

        jwtUtil.saveRefreshToken(refreshToken, manager, JwtProperties.REFRESH_EXPIRED_TIME);

        response.addHeader("AccessToken",accessToken);
        response.addHeader("RefreshToken", refreshToken);
        response.setContentType("application/json;charset=UTF-8");

        ManagerLoginResponse loginResponse = ManagerLoginResponse.from(manager);

        String body = new ObjectMapper().writeValueAsString(loginResponse);

        response.getWriter().write(body);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        jwtUtil.tokenErrorResponse(response, ErrorType.FAILED_LOGIN);
    }
}