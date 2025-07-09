package com.kbe5.infra.infrastructure.device;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.device.service.DeviceStore;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
import com.kbe5.infra.infrastructure.device.repository.DeviceRepository;
import com.kbe5.infra.infrastructure.device.repository.DeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceStoreImpl implements DeviceStore {

    private final DeviceRepository deviceRepository;
    private final DeviceTokenRepository deviceTokenRepository;

    @Override
    public Device store(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public void delete(Device device) {

    }

    @Override
    public DeviceToken storeToken(DeviceToken deviceToken) {
        return deviceTokenRepository.save(deviceToken);
    }

    @Override
    public DeviceToken deleteToken(String token) {
        DeviceToken deviceToken = deviceTokenRepository.findById(token)
            .orElseThrow(() -> new DeviceException(DeviceResultCode.UNUSABLE_TOKEN));
        deviceTokenRepository.deleteById(token);
        return deviceToken;
    }

    @Override
    public DeviceToken findDeviceToken(String deviceToken) {
        return deviceTokenRepository.findById(deviceToken)
            .orElseThrow(() -> new DeviceException(DeviceResultCode.INVALID_TOKEN));
    }
}
