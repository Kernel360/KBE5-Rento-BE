package com.kbe5.rento.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.entity.Device;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;

public record DeviceTokenResponse(
    @JsonProperty("rstCd")
    String resultCode,

    @JsonProperty("rstMsg")
    String resultMessage,

    @JsonProperty("mdn")
    Long mdn,

    String token,

    String exPeriod
) {

    public static DeviceTokenResponse of(DeviceResultCode resultCode, Device device, String token, String exPeriod) {
        return new DeviceTokenResponse(resultCode.getCode(), resultCode.getMessage(), device.getMdn(), token, exPeriod);
    }
}
