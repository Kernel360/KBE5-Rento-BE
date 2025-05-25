package com.kbe5.rento.common.jwt;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
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

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }

        // "Bearer token..." 형식으로 약속되어있기 때문에 빈칸을 기준으로 split 하여 토큰을 얻어온다.
        String token = authorization.split(" ")[1];

        if (jwtUtil.isExpired(token)) {

            filterChain.doFilter(request, response);

            return;
        }

        try {
            String loginId = jwtUtil.getLoginId(token);

            Manager manager = managerRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new DomainException(ErrorType.MANAGER_NOT_FOUND));

            CustomManagerDetails customManagerDetails = new CustomManagerDetails(manager);

            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customManagerDetails, null, customManagerDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            throw new DomainException(ErrorType.INVALID_TOKEN);
        }

        filterChain.doFilter(request, response);
    }
}
