package com.kbe5.rento.domain.device.controller;

import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.device.dto.request.OnEventRequest;
import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.rento.domain.device.dto.resonse.DeviceRegisterResponse;
import com.kbe5.rento.domain.device.dto.resonse.OnEventResponse;
import com.kbe5.rento.domain.device.entity.Device;
import com.kbe5.rento.domain.device.service.DeviceEventService;
import com.kbe5.rento.domain.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devices")
public class DeviceControllerImpl implements DeviceController{

    private final DeviceService deviceService;
    private final DeviceEventService deviceEventService;

    @PostMapping
    public ResponseEntity<DeviceRegisterResponse> registerDevice(
        @RequestBody @Validated DeviceRegisterRequest request) {

        Device device = request.toDevice();

        Device registerDevice = deviceService.registerDevice(device);

        DeviceRegisterResponse response = DeviceRegisterResponse.of(DeviceResultCode.SUCCESS, registerDevice);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/on")
    public ResponseEntity<OnEventResponse> onEvent(@RequestBody @Validated OnEventRequest request) {

        return ResponseEntity.ok(deviceEventService.ignitionOnEvent(request));
    }

}
