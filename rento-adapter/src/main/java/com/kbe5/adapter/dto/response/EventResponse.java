package com.kbe5.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.common.exception.DeviceResultCode;

public record EventResponse(

        @JsonProperty("rstCd")
        String resultCode,

        @JsonProperty("rstMsg")
        String resultMessage,

        @JsonProperty("mdn")
        Long mdn  //차량 번호
) {

    public static EventResponse fromEntity(DeviceResultCode resultCode, Long mdn) {
        return new EventResponse(resultCode.getCode(), resultCode.getMessage(), mdn);
    }
}
