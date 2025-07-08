package com.kbe5.domain.device.service;

import com.kbe5.domain.device.entity.Device;

public interface DeviceReader {

    boolean existsByMdn(Long mdn);

    Device findByMdn(Long mdn);
}
