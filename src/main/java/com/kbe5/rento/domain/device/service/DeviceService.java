package com.kbe5.rento.domain.device.service;

import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.rento.domain.device.entity.Device;
import com.kbe5.rento.domain.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Transactional
    public void registerDevice(DeviceRegisterRequest request) {

        validateDuplicateDevice(request);

        deviceRepository.save(Device.from(request));
    }

    private void validateDuplicateDevice(DeviceRegisterRequest request) {
        deviceRepository.findByMobileDeviceNumber(request.mobileDeviceNumber())
            .ifPresent(device -> {
                throw new IllegalArgumentException("이미 등록된 디바이스입니다.");
            });
    }

}
