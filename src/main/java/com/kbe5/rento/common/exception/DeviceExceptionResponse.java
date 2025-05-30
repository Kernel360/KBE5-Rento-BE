package com.kbe5.rento.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeviceExceptionResponse(
    @JsonProperty("rstCd")
    String resultCode,
    @JsonProperty("rstMsg")
    String message
) {
}
