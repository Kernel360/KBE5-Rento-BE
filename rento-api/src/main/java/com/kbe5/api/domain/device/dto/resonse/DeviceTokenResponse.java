package com.kbe5.api.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.common.exception.DeviceResultCode;
import com.kbe5.domain.device.entity.DeviceToken;


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
