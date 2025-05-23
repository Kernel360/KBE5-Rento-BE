package com.kbe5.rento.domain.device.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DeviceRegisterRequest(
    @NotBlank
    @JsonProperty("mdn")
    Long mobileDeviceNumber, //차량 번호

    @NotBlank
    @JsonProperty("tid")
    String terminalId, // A001로 고정

    @NotBlank
    @JsonProperty("mid")
    Integer manufacturerId, // 6으로 고정

    @Min(0)
    @Max(65535)
    @NotBlank
    @JsonProperty("pv")
    Integer packetVersion, // 5로 고정

    @NotBlank
    @JsonProperty("did")
    Integer deviceId

) {


}
