package com.kbe5.rento.domain.device.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kbe5.rento.common.datetime.LocalDateTimeDeserializer;
import com.kbe5.rento.common.datetime.EventLocalDateTimeDeserializer;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OnEventRequest(
    @JsonProperty("mdn")
    @NotNull(message = "{device.mdn.notnull}")
    Long mobileDeviceNumber, //차량 번호

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
    Long currentAccumulatedDistance
) {

}
