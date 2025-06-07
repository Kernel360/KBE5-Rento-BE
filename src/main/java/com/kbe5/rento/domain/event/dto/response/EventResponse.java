package com.kbe5.rento.domain.event.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.event.entity.Event;
import com.kbe5.rento.domain.event.entity.OnOffEvent;

public record EventResponse(

        @JsonProperty("rstCd")
        String resultCode,

        @JsonProperty("rstMsg")
        String resultMessage,

        @JsonProperty("mdn")
        Long mdn //차량 번호
) {

    public static EventResponse fromEntity(DeviceResultCode resultCode, Event event) {
        return new EventResponse(resultCode.getCode(), resultCode.getMessage(), event.getMdn());
    }
}
