package com.kbe5.rento.common.jwt.util;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.common.response.error.ErrorResponse;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import com.kbe5.rento.domain.manager.service.CustomMangerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ManagerRepository managerRepository;

    public JwtFilter(JwtUtil jwtUtil, ManagerRepository managerRepository) {
        this.jwtUtil = jwtUtil;
        this.managerRepository = managerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,@NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("AccessToken");

        if (accessToken == null || accessToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtil.isExpired(accessToken)) {
            ErrorResponse.SendError(response, ErrorType.EXPIRED_TOKEN);
            return;
        }

        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            ErrorResponse.SendError(response, ErrorType.INVALID_TOKEN);
            return;
        }

        CustomManagerDetails customManagerDetails =
                (CustomManagerDetails) new CustomMangerDetailsService(managerRepository)
                        .loadUserByUsername(jwtUtil.getLoginId(accessToken));

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customManagerDetails, null, customManagerDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
