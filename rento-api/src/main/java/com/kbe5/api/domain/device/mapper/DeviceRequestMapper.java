package com.kbe5.api.domain.device.mapper;

import com.kbe5.api.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.domain.device.dto.DeviceCommand;

public class DeviceRequestMapper {
    //todo:builder 채워넣기
    public static DeviceCommand.Register register(DeviceRegisterRequest request) {
        return DeviceCommand.Register.builder()
            .mdn(request.mdn())
            .terminalId(request.terminalId())
            .makerId(request.makerId())
            .packetVersion(request.packetVersion())
            .deviceId(request.deviceId())
            .companyCode(request.companyCode())
            .deviceFirmWareVersion("LTE 1.2")
            .build();
    }
    //todo:issuetoken,deleteDevice,deleteToken
}
