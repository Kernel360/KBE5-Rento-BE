package com.kbe5.api.domain.device.controller;


import com.kbe5.api.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.api.domain.device.dto.request.DeviceSettingRequest;
import com.kbe5.api.domain.device.dto.request.DeviceTokenRequest;
import com.kbe5.api.domain.device.dto.resonse.DeviceRegisterResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceSettingResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceTokenDeleteResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceTokenResponse;
import com.kbe5.api.domain.device.mapper.DeviceRequestMapper;
import com.kbe5.api.domain.device.mapper.DeviceResponseMapper;
import com.kbe5.domain.device.dto.DeviceCommand;
import com.kbe5.domain.device.dto.DeviceInfo;
import com.kbe5.domain.device.service.DeviceService;
import com.kbe5.domain.exception.DeviceResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

        DeviceCommand.Register command = DeviceRequestMapper.register(request);
        DeviceInfo.Register info = deviceService.registerDevice(command);

        DeviceRegisterResponse response = DeviceResponseMapper.toRegisterResponse(info,
            DeviceResultCode.SUCCESS);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public ResponseEntity<DeviceTokenResponse> issueToken(
        @RequestBody @Validated DeviceTokenRequest deviceTokenRequest) {

        log.info("디바이스 토큰 요청: {}", deviceTokenRequest.toString());

        DeviceInfo.IssueToken info = deviceService.issueToken(deviceTokenRequest.mdn());
        DeviceTokenResponse response = DeviceResponseMapper.toIssueTokenResponse(info,
            DeviceResultCode.SUCCESS);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("token")
    public ResponseEntity<DeviceTokenDeleteResponse> deleteToken(
        @RequestHeader("X-Device-Token") String deviceToken
        ) {
        DeviceInfo.DeleteToken info = deviceService.deleteToken(deviceToken);
        DeviceTokenDeleteResponse response = DeviceResponseMapper.toTokenDeleteResponse(info,
            DeviceResultCode.SUCCESS);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/get-set-info")
//    public ResponseEntity<DeviceSettingResponse> getSetInfo(@RequestBody @Validated
//                                                            DeviceSettingRequest request) {
//        log.info("디바이스 설정 정보: {}", request.toString());
//
//        return ResponseEntity.ok(deviceService.getDeviceSetInfo(request.mdn()));
//    }
}
