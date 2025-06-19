package com.kbe5.rento.domain.event.dto.request.cycleinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.event.entity.CycleEvent;
import com.kbe5.rento.domain.event.entity.CycleInfo;
import com.kbe5.rento.domain.event.enums.EventType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

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
    LocalDateTime oTime,

    @JsonProperty("cCnt")
    @NotNull
    Integer cycleCount,

    @JsonProperty("cList")
    @NotEmpty
    List<CycleInfoRequest> cycleInfoRequests
) {

    public CycleEvent toEntity(DeviceToken token) {
        return CycleEvent.builder()
            .oTime(this.oTime())
            .mdn(this.mdn())
            .terminalId(this.terminalId())
            .makerId(this.makerId())
            .packetVersion(this.packetVersion())
            .deviceId(this.deviceId())
            .cycleCount(this.cycleCount())
            .eventType(EventType.CYCLE_INFO)
            .driveId(token.getDriveId())
            .build();
    }

    public List<CycleInfo> toCycleInfoEntities(DeviceToken deviceToken) {
        return this.cycleInfoRequests().stream()
            .map(req -> req.toEntity(this.oTime(), deviceToken))
            .toList();
    }
}
