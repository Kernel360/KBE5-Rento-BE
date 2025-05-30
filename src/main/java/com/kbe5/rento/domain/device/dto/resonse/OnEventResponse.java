package com.kbe5.rento.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.entity.event.OnOffEvent;

public record OnEventResponse(
    @JsonProperty("mdn")
    Long mobileDeviceNumber//차량 번호
) {

    public static OnEventResponse fromEntity(OnOffEvent onOffEvent) {
        return new OnEventResponse(onOffEvent.getMobileDeviceNumber());
    }
}
