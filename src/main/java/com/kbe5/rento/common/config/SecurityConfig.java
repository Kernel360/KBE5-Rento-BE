package com.kbe5.rento.common.config;

import com.kbe5.rento.common.jwt.util.JwtFilter;
import com.kbe5.rento.common.jwt.device.DeviceTokenFilter;
import com.kbe5.rento.common.jwt.util.JwtUtil;
import com.kbe5.rento.common.securityFilter.LoginAuthenticationFilter;
import com.kbe5.rento.common.util.SecurityPermissionApiList;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final ManagerRepository managerRepository;
    private final DeviceTokenFilter deviceTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CORS 설정
        http.cors(cors -> cors
                .configurationSource(corsConfigurationSource()));

        // 보안 기본 설정 해제
        http.csrf(AbstractHttpConfigurer::disable) // 쿠키를 사용하지 않아서 필요 없음
                .formLogin(AbstractHttpConfigurer::disable) // formLogin 사용 안함
                .httpBasic(AbstractHttpConfigurer::disable);

        // 헤더 설정 (H2 콘솔을 위한 sameOrigin)
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        // 인가 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(SecurityPermissionApiList.PUBLIC_URLS).permitAll()
                .requestMatchers("/admin").hasRole("ADMIN") // 추 후 시스템 관리자 구현 및 권한 설정 시 사용
                .anyRequest().authenticated());

        // LoginFilter 추가
        http.addFilterBefore(deviceTokenFilter, LoginAuthenticationFilter.class);
        http.addFilterBefore(new JwtFilter(jwtUtil, managerRepository), LoginAuthenticationFilter.class);
        http.addFilterAt(new LoginAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 프론트 서버 포트로 변경 예정
        configuration.setAllowedMethods(Collections.singletonList("*")); // 추 후 https 만 허용
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L); // 요청 캐시 시간
        configuration.setExposedHeaders(Collections.singletonList("Authorization")); // 클라이언트에 노출할 해더

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
