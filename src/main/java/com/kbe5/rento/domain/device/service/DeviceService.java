package com.kbe5.rento.domain.device.service;

import com.kbe5.rento.domain.device.domain.DeviceControlInfo;
import com.kbe5.rento.domain.device.dto.request.DeviceSettingRequest;
import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.device.entity.Device;
import com.kbe5.rento.common.exception.DeviceException;
import com.kbe5.rento.domain.device.repository.DeviceRepository;
import com.kbe5.rento.domain.device.repository.DeviceTokenRepository;
import com.kbe5.rento.domain.geofence.entity.Geofence;
import com.kbe5.rento.domain.geofence.repository.GeofenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final GeofenceRepository geofenceRepository;

    private static final Long EXPIRED_MS = 4 * 60 * 60 * 1000L;

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

    @Transactional
    public DeviceToken issueToken(Long mdn){
        Device device = deviceRepository.findByMdn(mdn)
            .orElseThrow(() -> new DeviceException(DeviceResultCode.MISMATCHED_MDN));

        DeviceToken token = device.issueToken(EXPIRED_MS);

        return deviceTokenRepository.save(token);
    }

    @Transactional(readOnly = true)
    public DeviceToken validateAndGetToken(String token) {
        DeviceToken deviceToken = deviceTokenRepository.findByToken(token)
            .orElseThrow(() -> new DeviceException(DeviceResultCode.UNUSABLE_TOKEN));

        deviceToken.validateNotExpired();

        return deviceToken;
    }

    @Transactional
    public DeviceControlInfo getDeviceSetInfo(DeviceSettingRequest request) {
        Device device = deviceRepository.findByMdn(request.mdn())
                .orElseThrow(() -> new DeviceException(DeviceResultCode.MISMATCHED_MDN));

        List<Geofence> geofenceList = geofenceRepository.findAllByCompanyCode(device.getCompanyCode());
        return null;
    }

    @Transactional
    public void deleteToken(String token) {
        deviceTokenRepository.deleteByToken(token);
    }

}
