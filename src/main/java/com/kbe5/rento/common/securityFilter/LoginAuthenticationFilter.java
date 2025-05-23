package com.kbe5.rento.common.securityFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe5.rento.domain.manager.dto.request.ManagerLoginRequest;
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

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
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
        // jwt Toket 구현 후 적용 예졍
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 에러코드 적용 후 구현 예졍
    }
}
