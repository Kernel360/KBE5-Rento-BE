package com.kbe5.api.domain.stream.controller;


import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.domain.commonservice.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stream")
public class StreamController {

    private final StreamService streamService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal CustomManagerDetails manager) {
        log.info("[0️⃣ StreamController] {}번 매니저 SSE 구독 시작", manager.getManager().getId());
        return streamService.subscribe(manager.getManager().getId());
    }
}
