package com.kbe5.domain.device.service;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceToken;

public interface DeviceStore {

    Device store(Device device);
    void delete(Device device);
    DeviceToken storeToken(DeviceToken deviceToken);
    DeviceToken deleteToken(String token);
}
