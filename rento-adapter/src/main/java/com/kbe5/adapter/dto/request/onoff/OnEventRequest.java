package com.kbe5.adapter.dto.request.onoff;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.kbe5.common.util.EventLocalDateTimeDeserializer;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.entity.OnOffEvent;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.enums.GpsCondition;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OnEventRequest(

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

    @NotNull(message = "{device.onTime.notnull}")
    @JsonDeserialize(using = EventLocalDateTimeDeserializer.class)
    LocalDateTime onTime,

    @JsonDeserialize(using = EventLocalDateTimeDeserializer.class)
    LocalDateTime offTime,

    @JsonProperty("gcd")
    @NotNull(message = "{device.gpsCondition.notnull}")
    GpsCondition gpsCondition, // GPS 상태

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @JsonProperty("lat")
    @NotNull(message = "{device.event.lat.notnull}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#.######") // 소수점 6자리
    BigDecimal latitude,

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    @JsonProperty("lon")
    @NotNull(message = "{device.event.lon.notnull}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#.######") // 소수점 6자리
    BigDecimal longitude,

    @Min(0)
    @Max(365)
    @JsonProperty("ang")
    @NotNull(message = "{device.event.angle.notnull}")
    Integer angle,

    @Min(0)
    @Max(255)
    @JsonProperty("spd")
    @NotNull(message = "{device.event.speed.notnull}")
    Integer speed,

    @Min(0)
    @Max(9999999)
    @JsonProperty("sum")
    @NotNull(message = "{device.currentAccumulatedDistance.notnull}")
    Long sum
) {
    public OnOffEvent toEntity(DeviceToken token) {
        return OnOffEvent.builder()
            .oTime(this.onTime())
            .mdn(this.mdn())
            .terminalId(this.terminalId())
            .makerId(this.makerId())
            .packetVersion(this.packetVersion())
            .deviceId(this.deviceId())
            .gpsCondition(this.gpsCondition())
            .latitude(this.latitude())
            .longitude(this.longitude())
            .angle(this.angle())
            .speed(this.speed())
            .currentAccumulatedDistance(this.sum())
            .onTime(this.onTime())
            .offTime(this.offTime())
            .eventType(EventType.ON)
            .driveId(token.getDriveId())
            .build();
    }
}
