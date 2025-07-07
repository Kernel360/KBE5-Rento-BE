package com.kbe5.domain.device.dto;

import com.kbe5.domain.device.entity.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceInfo {

    private Long id;
    private Long mdn;

    public static DeviceInfo fromEntity(Device device) {
        return new DeviceInfo(device.getId(), device.getMdn());
    }

}
