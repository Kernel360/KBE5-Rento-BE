package com.kbe5.api.domain.device.dto.resonse;


import com.kbe5.domain.device.entity.DeviceControlInfo;

public record DeviceControlInfoResponse(
        long controlId,
        String controlCode,
        String controlValue
) {
}
