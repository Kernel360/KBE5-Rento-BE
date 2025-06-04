package com.kbe5.rento.domain.event.dto.response.onoff;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.event.entity.OnOffEvent;

public record OffEventResponse(

    @JsonProperty("rstCd")
    String resultCode,

    @JsonProperty("rstMsg")
    String resultMessage,

    @JsonProperty("mdn")
    Long mdn//차량 번호
) {
    public static OffEventResponse fromEntity(DeviceResultCode resultCode, OnOffEvent onOffEvent) {
        return new OffEventResponse(resultCode.getCode(), resultCode.getMessage(), onOffEvent.getMdn());
    }
}
