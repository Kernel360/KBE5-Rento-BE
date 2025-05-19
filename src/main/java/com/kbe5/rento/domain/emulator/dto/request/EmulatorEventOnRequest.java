package com.kbe5.rento.domain.emulator.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record EmulatorEventOnRequest(
    @NotBlank
    @JsonProperty("mdn")
    String mobileDeviceNumber, //차량 번호
    @NotBlank
    @JsonProperty("tid")
    String terminalId, // A001로 고정
    @NotBlank
    @JsonProperty("mid")
    String makerId, // 6으로 고정
    @Min(0)
    @Max(65535)
    @NotBlank
    @JsonProperty("pv")
    Integer packetVersion, // 5로 고정
    @NotBlank
    @JsonProperty("did")
    Integer deviceId, //1로 고정
    @NotBlank
    String onTime,
    @NotBlank
    String offTime,
    @NotBlank
    @JsonProperty("gcd")
    String gpsConditionDescription, // GPS 상태
    @NotBlank
    @JsonProperty("lat")
    Double latitude,
    @NotBlank
    @JsonProperty("lon")
    Double longitude,
    @Min(0)
    @Max(365)
    @NotBlank
    @JsonProperty("ang")
    Integer angle,
    @Min(0)
    @Max(255)
    @NotBlank
    @JsonProperty("spd")
    Integer speed
) {

}
