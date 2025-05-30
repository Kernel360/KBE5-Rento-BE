package com.kbe5.rento.domain.device.controller;

import com.kbe5.rento.common.jwt.device.DeviceAuthenticationToken;
import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.rento.domain.device.dto.request.OnEventRequest;
import com.kbe5.rento.domain.device.dto.resonse.DeviceRegisterResponse;
import com.kbe5.rento.domain.device.dto.resonse.OnEventResponse;
import com.kbe5.rento.domain.device.entity.DeviceToken;
import org.springframework.http.ResponseEntity;

public interface DeviceController {

    ResponseEntity<DeviceRegisterResponse> registerDevice(DeviceRegisterRequest request);

    ResponseEntity<OnEventResponse> onEvent(
        DeviceToken deviceToken,
        OnEventRequest request);

}
