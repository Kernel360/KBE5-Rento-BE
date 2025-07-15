package com.kbe5.pub.controller;

import com.kbe5.common.exception.DeviceResultCode;
import com.kbe5.domain.cycleinfosummary.service.CycleDataSummaryService;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.device.service.DeviceService;
import com.kbe5.domain.drive.service.DriveService;
import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.dto.EventCommand.GeofenceEventCommand;
import com.kbe5.domain.event.dto.EventCommand.OffEventCommand;
import com.kbe5.domain.event.dto.EventCommand.OnEventCommand;
import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.entity.GeofenceEvent;
import com.kbe5.pub.amqp.EventSender;
import com.kbe5.pub.amqp.NotificationSender;
import com.kbe5.pub.amqp.StreamSender;
import com.kbe5.pub.dto.request.cycleinfo.CycleEventRequest;
import com.kbe5.pub.dto.request.geofence.GeofenceEventRequest;
import com.kbe5.pub.dto.request.onoff.OffEventRequest;
import com.kbe5.pub.dto.request.onoff.OnEventRequest;
import com.kbe5.pub.dto.response.EventResponse;
import com.kbe5.pub.mapper.EventRequestMapper;
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
    private final DeviceService deviceService;
    private final NotificationSender notificationSender;
    private final StreamSender streamSender;
    private final CycleDataSummaryService cycleDataSummaryService;

    @PostMapping("/cycle-info")
    public ResponseEntity<EventResponse> emitCycleInfo(
        @RequestHeader("X-Device-Token") String token,
        @RequestBody @Validated CycleEventRequest request
    ) {
//        DeviceToken deviceToken = deviceService.findDeviceToken(token);
        Long mdn = request.mdn();

        EventCommand.CycleEventCommand command = EventRequestMapper.cycleEventCommand(request, token);
        eventSender.send(command);
//        streamSender.send(command, mdn);

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }


    @PostMapping("/on-off/on")
    public ResponseEntity<EventResponse> ignitionOn(
        @RequestHeader("X-Device-Token") String token,
        @RequestBody @Validated OnEventRequest request
    ){
//        DeviceToken deviceToken = deviceService.findDeviceToken(token);
        Long mdn = request.mdn();
//
//        driveService.driveStart(deviceToken.getDriveId());

        OnEventCommand command = EventRequestMapper.onEventCommand(request, token);
        eventSender.send(command);

        //fcm 알림 발송 큐
        //notificationSender.send(deviceToken.getDriveId());

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }

    @PostMapping("/on-off/off")
    public ResponseEntity<EventResponse> ignitionOff(
        @RequestHeader("X-Device-Token") String token,
        @RequestBody @Validated OffEventRequest request) {

        DeviceToken deviceToken = deviceService.findDeviceToken(token);
        Long mdn = request.mdn();

        log.info(request.sum().toString());
        driveService.driveEnd(deviceToken.getDriveId(), request.sum());

        OffEventCommand command = EventRequestMapper.offEventCommand(request, token);
        eventSender.send(command);

        cycleDataSummaryService.create(deviceToken.getDriveId());
        //fcm 알림 발송 큐
        //notificationSender.send(deviceToken.getDriveId());

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }

    @PostMapping("/geofences")
    public ResponseEntity<EventResponse> receiveGeofenceEvent (
        @RequestHeader("X-Device-Token") String token,
        @RequestBody @Validated GeofenceEventRequest request) {
//        DeviceToken deviceToken = deviceService.findDeviceToken(token);
        Long mdn = request.mdn();

        GeofenceEventCommand command = EventRequestMapper.geofenceEventCommand(request, token);
        eventSender.send(command);

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, request.mdn()));
    }
}
