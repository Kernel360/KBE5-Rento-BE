package com.kbe5.rento.domain.device.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceRegisterRequest(
    @JsonProperty("mdn")
    @NotNull(message = "mdn은 필수입니다.")
    Long mobileDeviceNumber, //차량 번호

    @JsonProperty("tid")
    @NotBlank(message = "tid는 필수입니다.")
    String terminalId, // A001로 고정

    @JsonProperty("mid")
    @NotNull(message = "mid는 필수입니다.")
    Integer manufacturerId, // 6으로 고정

    @Min(0)
    @Max(65535)
    @JsonProperty("pv")
    @NotNull(message = "pv는 필수입니다.")
    Integer packetVersion, // 5로 고정

    @JsonProperty("did")
    @NotNull(message = "did는 필수입니다.")
    Integer deviceId

) {


}
