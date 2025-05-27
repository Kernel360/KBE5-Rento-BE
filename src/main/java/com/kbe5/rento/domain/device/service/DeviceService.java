package com.kbe5.rento.domain.device.service;

import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.device.entity.Device;
import com.kbe5.rento.common.exception.DeviceException;
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
    public Device registerDevice(Device device) {

        validateDuplicateDevice(device.getMdn());

        return deviceRepository.save(device);
    }

    private void validateDuplicateDevice(Long mdn) {
        deviceRepository.findByMdn(mdn)
            .ifPresent(device -> {
                throw new DeviceException(DeviceResultCode.MISMATCHED_MDN);
            });
    }
}
