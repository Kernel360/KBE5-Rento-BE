package com.kbe5.rento.domain.device.controller;

import com.kbe5.rento.domain.device.dto.request.DeviceEventOnRequest;
import com.kbe5.rento.domain.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emulators")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/on")
    public void emulatorOnEvent(DeviceEventOnRequest requestDto) {
        //이벤트 정보를 수신합니다

    }

    @PostMapping("/off")
    public void emulatorOffEvent() {

    }

    //주기정보 전달
    @PostMapping("/control-info")
    public void emulatorControlInfo() {

    }

    //주기 리스트
    @PostMapping("/control-info/confirm")
    public void emulatorControlInfoConfirm() {

    }

    @PostMapping("/token")
    public void emulatorToken() {

    }
}
