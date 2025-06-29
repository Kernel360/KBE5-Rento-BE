package com.kbe5.adapter.dto.request.cycleinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.enums.GpsCondition;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CycleInfoRequest(

    @NotNull
    @Min(value = 0, message = "sec(발생시간)은 0 이상이어야 합니다.")
    @Max(value = 59, message = "sec(발생시간)은 59 이하이어야 합니다.")
    Integer sec,

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
    Long sum,

    @Min(0)
    @Max(9999)
    @JsonProperty("bat")
    @NotNull(message = "bat(배터리 전압)은 필수입니다.")
    Integer battery
){

    public CycleInfo of(LocalDateTime oTime, Long mdn, DeviceToken deviceToken) {
        return CycleInfo.builder()
            .cycleInfoTime(oTime.plusSeconds(this.sec()))
            .mdn(mdn)
            .driveId(deviceToken.getDriveId())
            .sec(this.sec())
            .gpsCondition(this.gpsCondition())
            .longitude(this.longitude())
            .latitude(this.latitude())
            .angle(this.angle())
            .speed(this.speed())
            .sum(this.sum())
            .battery(this.battery())
            .build();
    }
}
