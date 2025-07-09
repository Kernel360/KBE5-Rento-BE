package com.kbe5.domain.device.service;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceControlInfo;
import java.util.List;

public interface DeviceReader {

    void validateDuplicateDevice(Long mdn);

    Device findByMdn(Long mdn);

    List<DeviceControlInfo> findAllDeviceControlInfoByMdn(Long mdn);

}
