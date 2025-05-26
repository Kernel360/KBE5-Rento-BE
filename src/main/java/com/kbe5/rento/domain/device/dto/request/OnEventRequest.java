package com.kbe5.rento.domain.device.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OnEventRequest(
    @JsonProperty("mdn")
    @NotBlank(message = "mdn은 필수입니다.")
    Long mobileDeviceNumber, //차량 번호

    @JsonProperty("tid")
    @NotBlank
    String terminalId, // A001로 고정

    @JsonProperty("mid")
    @NotBlank
    String makerId, // 6으로 고정

    @Min(0)
    @Max(65535)
    @JsonProperty("pv")
    @NotNull
    Integer packetVersion, // 5로 고정

    @JsonProperty("did")
    @NotNull
    Integer deviceId, //1로 고정

    @NotNull
    String onTime,

    @NotNull
    String offTime,

    @NotBlank
    @JsonProperty("gcd")
    GpsCondition gpsCondition, // GPS 상태

    @NotBlank
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @JsonProperty("lat")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#.######") // 소수점 6자리
    BigDecimal latitude,

    @NotBlank
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    @JsonProperty("lon")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#.######") // 소수점 6자리
    BigDecimal longitude,

    @Min(0)
    @Max(365)
    @NotBlank
    @JsonProperty("ang")
    Integer angle,

    @Min(0)
    @Max(255)
    @NotBlank
    @JsonProperty("spd")
    Integer speed,

    @Min(0)
    @Max(9999999)
    @NotBlank
    @JsonProperty("sum")
    Long currentAccumulatedDistance
) {

}
