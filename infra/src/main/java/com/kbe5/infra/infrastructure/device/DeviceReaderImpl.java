package com.kbe5.infra.infrastructure.device;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceControlInfo;
import com.kbe5.domain.device.entity.GeofenceControlInfo;
import com.kbe5.domain.device.service.DeviceReader;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
import com.kbe5.infra.infrastructure.device.repository.DeviceControlInfoRepository;
import com.kbe5.infra.infrastructure.device.repository.DeviceRepository;
import com.kbe5.infra.infrastructure.device.repository.GeofenceControlInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceReaderImpl implements DeviceReader {

    private final DeviceRepository deviceRepository;
    private final DeviceControlInfoRepository deviceControlInfoRepository;
    private final GeofenceControlInfoRepository geofenceControlInfoRepository;

    public void validateDuplicateDevice(Long mdn) {
        boolean present = deviceRepository.findByMdn(mdn).isPresent();
        if (present) {
            throw new DeviceException(DeviceResultCode.MISMATCHED_MDN);
        }
    }

    @Override
    public Device findByMdn(Long mdn) {
        return deviceRepository.findByMdn(mdn).orElseThrow(() ->
            new DeviceException(DeviceResultCode.MISMATCHED_MDN)
        );
    }

    @Override
    public List<DeviceControlInfo> findAllDeviceControlInfoByMdn(Long mdn) {
        return deviceControlInfoRepository.findAllByMdn(mdn);
    }

    @Override
    public List<GeofenceControlInfo> findAllGeofenceControlInfoByMdn(Long mdn) {
        return geofenceControlInfoRepository.findAllByMdn(mdn);
    }

}
