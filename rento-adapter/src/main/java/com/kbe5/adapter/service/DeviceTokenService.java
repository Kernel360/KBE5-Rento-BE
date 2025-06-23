package com.kbe5.adapter.service;

import com.kbe5.common.exception.DeviceException;
import com.kbe5.common.exception.DeviceResultCode;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.device.repository.DeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    public DeviceToken findDeviceToken(String deviceToken) {
        return deviceTokenRepository.findById(deviceToken)
            .orElseThrow(() -> new DeviceException(DeviceResultCode.INVALID_TOKEN));
    }
}
