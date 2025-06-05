package com.kbe5.rento.domain.device.dto.resonse;

import com.kbe5.rento.domain.device.domain.DeviceControlInfo;

public record DeviceControlInfoResponse(
        long controlId,
        String controlCode,
        int controlValue
) {
    public DeviceControlInfoResponse fromDomain(DeviceControlInfo domain) {
        return new DeviceControlInfoResponse(
                domain.getControlId(),
                domain.getControlCode(),
                domain.getControlValue());
    }
}
