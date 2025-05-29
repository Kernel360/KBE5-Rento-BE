package com.kbe5.rento.domain.device.controller;

import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.rento.domain.device.dto.request.OnEventRequest;
import com.kbe5.rento.domain.device.dto.resonse.DeviceRegisterResponse;
import com.kbe5.rento.domain.device.dto.resonse.OnEventResponse;
import org.springframework.http.ResponseEntity;

public interface DeviceController {

    ResponseEntity<DeviceRegisterResponse> registerDevice(DeviceRegisterRequest request);

    ResponseEntity<OnEventResponse> onEvent(OnEventRequest request);

}
