package com.kbe5.api.domain.device.controller;


import com.kbe5.api.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.api.domain.device.dto.request.DeviceSettingRequest;
import com.kbe5.api.domain.device.dto.request.DeviceTokenRequest;
import com.kbe5.api.domain.device.dto.resonse.DeviceRegisterResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceSettingResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceTokenResponse;
import org.springframework.http.ResponseEntity;

public interface DeviceController {

    ResponseEntity<DeviceRegisterResponse> registerDevice(DeviceRegisterRequest request);

    ResponseEntity<DeviceTokenResponse> issueToken(DeviceTokenRequest deviceTokenRequest);

    ResponseEntity<DeviceSettingResponse> getSetInfo(DeviceSettingRequest deviceSettingRequest);

}
