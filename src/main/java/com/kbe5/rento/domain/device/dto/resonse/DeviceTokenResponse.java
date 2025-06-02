package com.kbe5.rento.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;

public record DeviceTokenResponse(

    @JsonProperty("rstCd")
    String resultCode,

    @JsonProperty("rstMsg")
    String resultMessage,

    @JsonProperty("mdn")
    Long mdn,

    String token,

    @JsonProperty("exPeriod")
    Long exPeriod
) {

    public static DeviceTokenResponse of(DeviceResultCode resultCode, DeviceToken deviceToken) {

        return new DeviceTokenResponse(
            resultCode.getCode(), resultCode.getMessage(), deviceToken.getMdn(), deviceToken.getToken(),
            deviceToken.getExPeriod());
    }
}
