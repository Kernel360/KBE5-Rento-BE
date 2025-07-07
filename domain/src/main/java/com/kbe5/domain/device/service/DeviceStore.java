package com.kbe5.domain.device.service;

import com.kbe5.domain.device.entity.Device;

public interface DeviceStore {

    Device store(Device device);
    void delete(Device device);
}
