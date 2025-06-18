package com.kbe5.rento.domain.event.controller;

import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.service.DriveService;
import com.kbe5.rento.domain.event.amqp.EventSender;
import com.kbe5.rento.domain.event.dto.request.cycleinfo.CycleEventRequest;
import com.kbe5.rento.domain.event.dto.request.onoff.OffEventRequest;
import com.kbe5.rento.domain.event.dto.request.onoff.OnEventRequest;
import com.kbe5.rento.domain.event.dto.response.EventResponse;
import com.kbe5.rento.domain.event.entity.CycleEvent;
import com.kbe5.rento.domain.event.entity.CycleInfo;
import com.kbe5.rento.domain.event.entity.OnOffEvent;
import com.kbe5.rento.domain.firebase.dto.TokenNotificationRequest;
import com.kbe5.rento.domain.firebase.service.FcmService;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventSender eventSender;
    private final DriveService driveService;
    private final FcmService fcmService;
    private final ManagerRepository managerRepository;

    @PostMapping("/on-off/on")
    public ResponseEntity<EventResponse> ignitionOn(
        @AuthenticationPrincipal DeviceToken deviceToken,
        @RequestBody @Validated OnEventRequest request
    ){

        Long mdn = request.mdn();

        driveService.driveStart(deviceToken.getDriveId());

        Drive drive = driveService.getDriveDetail(deviceToken.getDriveId());
        String vehicleNumber = drive.getVehicle().getInfo().vehicleNumber();

        List<Manager> managers = managerRepository.findAllByCompany(drive.getVehicle().getCompany());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        //todo: 지도 완성되면 지도 url 추가하기 -> null 주면 오류남!
        TokenNotificationRequest notificationRequest = new TokenNotificationRequest("운행 시작", vehicleNumber + "의 운행이 시작되었습니다\n" + now, "http://localhost:3000");

        fcmService.send(notificationRequest, managers);

        OnOffEvent onOffEvent = request.toEntity(deviceToken);
        eventSender.send(onOffEvent, mdn);

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }

    @PostMapping("/on-off/off")
    public ResponseEntity<EventResponse> ignitionOff(
        @AuthenticationPrincipal DeviceToken deviceToken,
        @RequestBody @Validated OffEventRequest request) {

        Long mdn = request.mdn();

        driveService.driveEnd(deviceToken.getDriveId(), request.currentAccumulatedDistance());

        OnOffEvent onOffEvent = request.toEntity(deviceToken);
        eventSender.send(onOffEvent, mdn);


        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }

    @PostMapping("/cycle-info")
    public ResponseEntity<EventResponse> emitCycleInfo(
        @AuthenticationPrincipal DeviceToken deviceToken,
        @RequestBody @Validated CycleEventRequest request
    ) {

        Long mdn = request.mdn();

        CycleEvent cycleEvent = request.toEntity(deviceToken);

        List<CycleInfo> cycleInfoList = request.toCycleInfoEntities(deviceToken);

        cycleEvent.setCycleInfos(cycleInfoList);

        eventSender.send(cycleEvent, mdn);

        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, mdn));
    }
}
