package com.kbe5.rento.domain.device.dto.resonse;

import com.kbe5.rento.domain.device.entity.DeviceControlInfo;

public record DeviceControlInfoResponse(
        long controlId,
        String controlCode,
        String controlValue
) {
    public static DeviceControlInfoResponse fromEntity(DeviceControlInfo entity) {
        return new DeviceControlInfoResponse(
                entity.getId(),
                entity.getCtrCd(),
                entity.getCtrVal());
    }
}
