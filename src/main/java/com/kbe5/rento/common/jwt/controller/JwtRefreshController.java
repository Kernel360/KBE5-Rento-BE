package com.kbe5.rento.common.jwt.controller;

import com.kbe5.rento.common.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers")
public class JwtRefreshController {

    private final JwtUtil jwtUtil;

    @PostMapping("/refresh")
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        jwtUtil.getNewAccessToken(request, response);
    }
}