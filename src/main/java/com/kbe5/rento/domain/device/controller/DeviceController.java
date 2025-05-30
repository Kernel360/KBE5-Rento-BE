package com.kbe5.rento.domain.device.controller;

import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.rento.domain.device.dto.resonse.DeviceRegisterResponse;
import org.springframework.http.ResponseEntity;

public interface DeviceController {

    ResponseEntity<DeviceRegisterResponse> registerDevice(DeviceRegisterRequest request);

}
