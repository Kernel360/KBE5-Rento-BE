package com.kbe5.api.domain.jwt.controller;

import com.kbe5.api.domain.jwt.dto.request.PayloadDecryptRequest;
import com.kbe5.api.domain.jwt.dto.response.PayloadDecryptResponse;
import com.kbe5.api.domain.jwt.util.JwtUtil;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
public class JwtController {

    private final JwtUtil jwtUtil;

    @PostMapping("/refresh")
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        jwtUtil.getNewAccessToken(request, response);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<ApiResponse<PayloadDecryptResponse>> decryptPayload(
            @RequestBody @Validated PayloadDecryptRequest request) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, jwtUtil.getDecryptData(request));
    }
}