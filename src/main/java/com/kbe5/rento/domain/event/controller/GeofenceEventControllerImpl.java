//package com.kbe5.rento.domain.event.controller;
//
//import com.kbe5.rento.domain.device.entity.DeviceToken;
//import com.kbe5.rento.domain.device.enums.DeviceResultCode;
//import com.kbe5.rento.domain.event.dto.request.geofence.GeofenceEventRequest;
//import com.kbe5.rento.domain.event.dto.response.EventResponse;
//import com.kbe5.rento.domain.event.entity.GeofenceEvent;
//import com.kbe5.rento.domain.event.service.EventService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/events/geofences")
//public class GeofenceEventControllerImpl implements GeofenceEventController{
//
//    private final EventService eventService;
//
//    @PostMapping
//    public ResponseEntity<EventResponse> receiveGeofenceEvent (@AuthenticationPrincipal DeviceToken deviceToken,
//                                                 @RequestBody @Validated GeofenceEventRequest request) {
//
//        GeofenceEvent geofenceEvent = request.toEntity(deviceToken.getDeviceId());
//        GeofenceEvent savedEvent = eventService.ignitionOnEvent(geofenceEvent, deviceToken);
//
//        return ResponseEntity.ok(EventResponse.fromEntity(DeviceResultCode.SUCCESS, savedEvent));
//    }
//}
