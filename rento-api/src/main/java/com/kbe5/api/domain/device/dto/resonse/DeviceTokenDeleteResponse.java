package com.kbe5.api.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeviceTokenDeleteResponse(
    @JsonProperty("rstCd")
    String resultCode,

    @JsonProperty("rstMsg")
    String resultMessage,

    String token) {
}
