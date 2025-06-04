package com.kbe5.rento.common.securityFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.common.jwt.dto.JwtManagerArgumentDto;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

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
            throw new DomainException(ErrorType.FAILED_LOGIN);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomManagerDetails customManagerDetails = (CustomManagerDetails) authResult.getPrincipal();

        Manager manager = customManagerDetails.getManager();
        JwtManagerArgumentDto managerArgumentDto = JwtManagerArgumentDto.fromEntity(manager);

        String accessToken = jwtUtil.createJwt("access", managerArgumentDto, JwtProperties.ACCESS_EXPIRED_TIME);
        String refreshToken = jwtUtil.createJwt("refresh", managerArgumentDto, JwtProperties.REFRESH_EXPIRED_TIME);

        jwtUtil.saveRefreshToken(refreshToken, manager, JwtProperties.REFRESH_EXPIRED_TIME);

        response.addHeader("AccessToken",accessToken);
        response.addHeader("RefreshToken", refreshToken);
        response.setContentType("application/json;charset=UTF-8");

        ManagerLoginResponse loginResponse = ManagerLoginResponse.fromEntity(manager);

        String body = new ObjectMapper().writeValueAsString(loginResponse);

        response.getWriter().write(body);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        DomainException domainException = new DomainException(ErrorType.FAILED_LOGIN);
        jwtUtil.tokenErrorResponse(response, domainException);
    }
}