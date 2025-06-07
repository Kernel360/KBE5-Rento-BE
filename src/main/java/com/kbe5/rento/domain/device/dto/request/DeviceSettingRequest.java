package com.kbe5.rento.domain.device.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DeviceSettingRequest(
        @JsonProperty("mdn")
        @NotNull(message = "{device.mdn.notnull}")
        Long mdn,// 차량 번호

        @JsonProperty("tid")
        @NotBlank(message = "{device.tid.notblank}")
        String terminalId, // A001 고정

        @JsonProperty("mid")
        @NotNull(message = "{device.mid.notnull}")
        Integer makerId, // 6 고정

        @JsonProperty("pv")
        @NotNull(message = "{device.pv.notnull}")
        @Min(value = 0, message = "{device.pv.min}")
        @Max(value = 65535, message = "{device.pv.max}")
        Integer packetVersion, // 5 고정

        @JsonProperty("did")
        @NotNull(message = "{device.did.notnull}")
        Integer deviceId,

        @NotNull(message = "{device.onTime.notnull}")
        LocalDateTime onTime,

        @JsonProperty("dFWVer")
        @NotNull(message = "{device.dFWVer.notnull}")
        String deviceFirmwareVersion // 'LTE 1.2' 로 고정
) {
}
