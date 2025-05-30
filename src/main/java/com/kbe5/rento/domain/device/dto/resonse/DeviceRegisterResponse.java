package com.kbe5.rento.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.device.entity.Device;

public record DeviceRegisterResponse(
    @JsonProperty("rstCd")
    String resultCode,

    @JsonProperty("rstMsg")
    String resultMessage,

    @JsonProperty("mdn")
    Long mdn
){

    public static DeviceRegisterResponse of(DeviceResultCode resultCode, Device device) {
        return new DeviceRegisterResponse(resultCode.getCode(), resultCode.getMessage(), device.getMdn());
    }
}
