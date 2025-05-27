package com.kbe5.rento.domain.device.service;

import com.kbe5.rento.common.jwt.JwtUtil;
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

    private final JwtUtil jwtUtil;

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

//    public String issueToken(Device device){
//        Device findDevice = deviceRepository.findByMobileDeviceNumber(request.mobileDeviceNumber())
//            .orElseThrow(() -> new IllegalArgumentException("등록된 디바이스가 없습니다."));
//
//        long expiredMs = 4 * 60 * 60 * 1000L; // 4시간 (14,400,000 ms)
//        String deviceJwt = jwtUtil.createDeviceJwt(device, expiredMs);
//    }

}
