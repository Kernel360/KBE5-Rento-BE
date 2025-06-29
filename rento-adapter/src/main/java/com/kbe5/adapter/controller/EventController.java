package com.kbe5.adapter.controller;

import com.kbe5.adapter.amqp.EventSender;
import com.kbe5.adapter.amqp.NotificationSender;
import com.kbe5.adapter.dto.request.cycleinfo.CycleEventRequest;
import com.kbe5.adapter.dto.request.geofence.GeofenceEventRequest;
import com.kbe5.adapter.dto.request.onoff.OffEventRequest;
import com.kbe5.adapter.dto.request.onoff.OnEventRequest;
import com.kbe5.adapter.dto.response.EventResponse;
import com.kbe5.adapter.service.DeviceTokenService;
import com.kbe5.common.exception.DeviceResultCode;
import com.kbe5.adapter.service.DriveService;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.entity.GeofenceEvent;
import com.kbe5.domain.event.entity.OnOffEvent;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Hidden
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventSender eventSender;
    private final DriveService driveService;
    private final DeviceTokenService deviceTokenService;
    private final NotificationSender notificationSender;

    @PostMapping("/on-off/on")
    public ResponseEntity<EventResponse> ignitionOn(
        @RequestHeader("X-Device-Token") String token,
        @RequestBody @Validated OnEventRequest request
    ){
        DeviceToken deviceToken = deviceTokenService.findDeviceToken(token);
        Long mdn = request.mdn();

        driveService.driveStart(deviceToken.getDriveId());

        OnOffEvent onOffEvent = request.toEntity(deviceToken);
        eventSender.send(onOffEvent, mdn);

        //fcm 알림 발송 큐
        //notificationSender.send(deviceToken.getDriveId());

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }

    @PostMapping("/on-off/off")
    public ResponseEntity<EventResponse> ignitionOff(
        @RequestHeader("X-Device-Token") String token,
        @RequestBody @Validated OffEventRequest request) {

        DeviceToken deviceToken = deviceTokenService.findDeviceToken(token);
        Long mdn = request.mdn();

        driveService.driveEnd(deviceToken.getDriveId(), request.currentAccumulatedDistance());

        OnOffEvent onOffEvent = request.toEntity(deviceToken);
        eventSender.send(onOffEvent, mdn);

        //fcm 알림 발송 큐
        //notificationSender.send(deviceToken.getDriveId());

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }

    @PostMapping("/cycle-info")
    public ResponseEntity<EventResponse> emitCycleInfo(
        @RequestHeader("X-Device-Token") String token,
        @RequestBody @Validated CycleEventRequest request
    ) {
        DeviceToken deviceToken = deviceTokenService.findDeviceToken(token);
        Long mdn = request.mdn();

        List<CycleInfo> cycleInfos = request.toCycleInfoEntities(deviceToken);
        CycleEvent cycleEvent = request.of(deviceToken, cycleInfos);

        eventSender.send(cycleEvent, mdn);

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }

    @PostMapping("/geofences")
    public ResponseEntity<EventResponse> receiveGeofenceEvent (
        @RequestHeader("X-Device-Token") String token,
        @RequestBody @Validated GeofenceEventRequest request) {
        DeviceToken deviceToken = deviceTokenService.findDeviceToken(token);

        GeofenceEvent geofenceEvent = request.toEntity(deviceToken.getDriveId());
        eventSender.send(geofenceEvent, request.mdn());

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, request.mdn()));
    }
}

