package com.kbe5.pub.dto.request.cycleinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kbe5.common.exception.DeviceException;
import com.kbe5.common.exception.DeviceResultCode;
import com.kbe5.common.util.EventLocalDateTimeDeserializer;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.enums.EventType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CycleEventRequest(
    @JsonProperty("mdn")
    @NotNull(message = "{device.mdn.notnull}")
    Long mdn, //차량 번호

    @JsonProperty("tid")
    @NotBlank(message = "{device.tid.notblank}")
    String terminalId, // A001로 고정

    @JsonProperty("mid")
    @NotNull(message = "{device.mid.notnull}")
    Integer makerId, // 6으로 고정

    @Min(0)
    @Max(65535)
    @JsonProperty("pv")
    @NotNull(message = "{device.pv.notnull}")
    Integer packetVersion, // 5로 고정

    @JsonProperty("did")
    @NotNull(message = "{device.did.notnull}")
    Integer deviceId, //1로 고정

    @JsonProperty("oTime")
    @NotNull
    @JsonDeserialize(using = EventLocalDateTimeDeserializer.class)
    LocalDateTime oTime,

    @JsonProperty("cCnt")
    @NotNull
    Integer cycleCount,

    @JsonProperty("cList")
    @NotEmpty
    List<CycleInfoRequest> cycleInfoRequests
) {
}
