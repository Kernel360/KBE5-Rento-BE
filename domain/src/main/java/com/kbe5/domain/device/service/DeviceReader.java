package com.kbe5.domain.device.service;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceControlInfo;
import com.kbe5.domain.device.entity.GeofenceControlInfo;
import java.util.List;

public interface DeviceReader {

    void validateDuplicateDevice(Long mdn);

    Device findByMdn(Long mdn);

    List<DeviceControlInfo> findAllDeviceControlInfoByMdn(Long mdn);

    List<GeofenceControlInfo> findAllGeofenceControlInfoByMdn(Long mdn);
}
