package com.kbe5.rento.domain.event.controller;

import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.event.dto.request.onoff.OnEventRequest;
import com.kbe5.rento.domain.event.dto.response.onoff.OnEventResponse;
import org.springframework.http.ResponseEntity;

public interface OnOffController {

    ResponseEntity<OnEventResponse> onEvent(DeviceToken deviceToken, OnEventRequest request);
}
