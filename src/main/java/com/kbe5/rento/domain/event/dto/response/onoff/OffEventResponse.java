package com.kbe5.rento.domain.event.dto.response.onoff;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.event.entity.OnOffEvent;

public record OffEventResponse(
    @JsonProperty("mdn")
    Long mdn//차량 번호
) {
    public static OffEventResponse fromEntity(OnOffEvent onOffEvent) {
        return new OffEventResponse(onOffEvent.getMdn());
    }
}
