package com.kbe5.rento.domain.event.dto.response.cycleinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.event.entity.CycleEvent;

public record CycleEventResponse(

    @JsonProperty("rstCd")
    String resultCode,

    @JsonProperty("rstMsg")
    String resultMessage,

    @JsonProperty("mdn")
    Long mdn
) {

    public static CycleEventResponse fromEntity(DeviceResultCode resultCode, CycleEvent cycleEvent) {
        return new CycleEventResponse(resultCode.getCode(), resultCode.getMessage(),cycleEvent.getMdn());
    }
}
