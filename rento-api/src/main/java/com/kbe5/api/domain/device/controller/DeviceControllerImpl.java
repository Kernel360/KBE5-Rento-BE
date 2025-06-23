package com.kbe5.api.domain.device.controller;


import com.kbe5.api.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.api.domain.device.dto.request.DeviceSettingRequest;
import com.kbe5.api.domain.device.dto.request.DeviceTokenRequest;
import com.kbe5.api.domain.device.dto.resonse.DeviceRegisterResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceSettingResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceTokenResponse;
import com.kbe5.api.domain.device.service.DeviceService;
import com.kbe5.common.exception.DeviceResultCode;
import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceToken;
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

    @PostMapping
    public ResponseEntity<DeviceRegisterResponse> registerDevice(
        @RequestBody @Validated DeviceRegisterRequest request) {

        Device device = request.toEntity();

        Device registerDevice = deviceService.registerDevice(device);

        DeviceRegisterResponse response = DeviceRegisterResponse.of(DeviceResultCode.SUCCESS, registerDevice);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public ResponseEntity<DeviceTokenResponse> issueToken(
        @RequestBody @Validated DeviceTokenRequest deviceTokenRequest) {

        DeviceToken deviceToken = deviceService.issueToken(deviceTokenRequest.mdn());

        return ResponseEntity.ok(DeviceTokenResponse.of(DeviceResultCode.SUCCESS, deviceToken));
    }

    @PostMapping("/get-set-info")
    public ResponseEntity<DeviceSettingResponse> getSetInfo(@RequestBody @Validated
                                                            DeviceSettingRequest request) {
        return ResponseEntity.ok(deviceService.getDeviceSetInfo(request.mdn()));
    }
}
