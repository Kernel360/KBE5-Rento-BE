package com.kbe5.rento.domain.event.dto.response.onoff;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.event.entity.OnOffEvent;

public record OnEventResponse(

    @JsonProperty("mdn")
    Long mdn//차량 번호
) {

    public static OnEventResponse fromEntity(OnOffEvent onOffEvent) {
        return new OnEventResponse(onOffEvent.getMdn());
    }
}
