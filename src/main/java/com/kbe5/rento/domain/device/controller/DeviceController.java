package com.kbe5.rento.domain.device.controller;

import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.rento.domain.device.dto.request.OnEventRequest;
import com.kbe5.rento.domain.device.dto.resonse.OnEventResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DeviceController {

    void registerDevice(DeviceRegisterRequest request);

    ResponseEntity<OnEventResponse> onEvent(OnEventRequest request);

}
