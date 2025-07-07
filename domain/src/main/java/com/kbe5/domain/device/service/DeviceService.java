package com.kbe5.domain.device.service;

import com.kbe5.domain.device.dto.DeviceCommand;
import com.kbe5.domain.device.dto.DeviceInfo;
import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceToken;

public interface DeviceService {

    DeviceInfo registerDevice(DeviceCommand.Register command);

//    DeviceSettingResponse getDeviceSetInfo(Long mdn);

    DeviceToken issueToken(Long mdn);

    DeviceToken validateAndGetToken(String token);

    void deleteToken(String token);
}
