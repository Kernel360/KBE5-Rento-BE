package com.kbe5.api.domain.stream.controller;


import com.kbe5.api.domain.jwt.util.JwtUtil;
import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.api.domain.manager.service.CustomMangerDetailsService;
import com.kbe5.api.domain.stream.service.StreamService;
import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import com.kbe5.common.util.Aes256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stream")
public class StreamController {

    private final StreamService streamService;

    private final Aes256Util aes256Util;

    private final CustomMangerDetailsService customMangerDetailsService;
    private final JwtUtil jwtUtil;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam("token") String token) {

        // 토큰 유효성 검사
        if (token == null || token.isBlank() ||
                jwtUtil.isExpired(token) ||
                !"access".equals(jwtUtil.getCategory(token))) {
            throw new DomainException(ErrorType.EXPIRED_TOKEN_ACCESS);
        }

        // 토큰에서 loginId 꺼내서 UserDetails 로드
        String loginId = aes256Util.AES_Decode(jwtUtil.getLoginId(token));

        CustomManagerDetails userDetails =
                (CustomManagerDetails) customMangerDetailsService.loadUserByUsername(loginId);

        Long managerId = userDetails.getManager().getId();
        Long companyId = userDetails.getManager().getCompany().getId();

        log.info("[StreamController] manager={} SSE 구독 시작", managerId);

        SseEmitter emitter = streamService.subscribe(managerId, companyId);

        emitter.onCompletion(() ->
                log.info("SSE 완료 매니저{}", managerId)
        );
        emitter.onTimeout(() ->
                log.warn("SSE 타임 아웃 매니저{}", managerId)
        );
        emitter.onError(e ->
                log.error("SSE 에러 발생 매니저{}: {}", managerId, e.getMessage())
        );

        return emitter;
    }
}
