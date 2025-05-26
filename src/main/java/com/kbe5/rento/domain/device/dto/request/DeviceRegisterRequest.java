package com.kbe5.rento.domain.device.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceRegisterRequest(
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
    Integer deviceId

) {


}
