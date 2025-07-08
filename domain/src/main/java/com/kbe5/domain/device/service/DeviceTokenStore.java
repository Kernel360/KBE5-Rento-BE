package com.kbe5.domain.device.service;

import com.kbe5.domain.device.entity.DeviceToken;

public interface DeviceTokenStore {

    DeviceToken store(DeviceToken deviceToken);
}
