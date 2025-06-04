package com.kbe5.rento.domain.event.controller;

import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.event.dto.request.cycleinfo.CycleEventRequest;
import com.kbe5.rento.domain.event.dto.response.cycleinfo.CycleEventResponse;
import com.kbe5.rento.domain.event.entity.CycleEvent;
import com.kbe5.rento.domain.event.entity.CycleInfo;
import com.kbe5.rento.domain.event.service.CycleInfoService;
import com.kbe5.rento.domain.event.service.EventService;
import java.util.List;
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
@RequestMapping("/api/events/cycle-info")
public class CycleInfoControllerImpl implements CycleInfoController{

    private final CycleInfoService cycleInfoService;

    private final EventService eventService;


    @PostMapping
    public ResponseEntity<CycleEventResponse> save(
        @AuthenticationPrincipal DeviceToken deviceToken,
        @RequestBody @Validated CycleEventRequest request
    ) {

        CycleEvent cycleEvent = request.toEntity(deviceToken.getDeviceId());

        List<CycleInfo> cycleInfoList = request.toCycleInfoEntities(deviceToken);
        cycleInfoService.saveCycleInfo(cycleInfoList);

        CycleEvent saveCycleEvent = eventService.saveCycleEvent(cycleEvent);

        return ResponseEntity.ok(CycleEventResponse.fromEntity(DeviceResultCode.SUCCESS, saveCycleEvent));
    }
}
