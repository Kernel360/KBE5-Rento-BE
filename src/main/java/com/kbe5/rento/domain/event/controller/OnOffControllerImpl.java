package com.kbe5.rento.domain.event.controller;

import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.event.dto.request.onoff.OffEventRequest;
import com.kbe5.rento.domain.event.dto.request.onoff.OnEventRequest;
import com.kbe5.rento.domain.event.dto.response.onoff.OffEventResponse;
import com.kbe5.rento.domain.event.dto.response.onoff.OnEventResponse;
import com.kbe5.rento.domain.event.entity.OnOffEvent;
import com.kbe5.rento.domain.event.service.OnOffEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events/on-off")
public class OnOffControllerImpl implements OnOffController{

    private final OnOffEventService onOffEventService;

    @PostMapping("/on")
    public ResponseEntity<OnEventResponse> onEvent(
        @AuthenticationPrincipal DeviceToken deviceToken, // 바로 DeviceToken!
        @RequestBody @Validated OnEventRequest request) {

        OnOffEvent onOffEvent = request.toEntity(deviceToken.getDeviceId());
        OnOffEvent savedEvent = onOffEventService.ignitionOnEvent(onOffEvent, deviceToken);

        return ResponseEntity.ok(OnEventResponse.fromEntity(savedEvent));
    }

    /**
     * 전송이 되지 않거나 실패할 경우 다음 ON 데이터를 전송 할 때, 시동 OFF 데이터를 같이 보낸다
     */
    @PostMapping("/off")
    public ResponseEntity<OffEventResponse> offEvent(
        @AuthenticationPrincipal DeviceToken deviceToken,
        @RequestBody @Validated OffEventRequest request) {

        OnOffEvent onOffEvent = request.toEntity(deviceToken.getDeviceId());
        OnOffEvent savedEvent = onOffEventService.iginitionOffEvent(onOffEvent, deviceToken);

        return ResponseEntity.ok(OffEventResponse.fromEntity(savedEvent));
    }
}
