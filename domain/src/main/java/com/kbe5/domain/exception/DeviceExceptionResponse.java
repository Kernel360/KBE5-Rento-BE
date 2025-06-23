package com.kbe5.domain.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeviceExceptionResponse(
    @JsonProperty("rstCd")
    String resultCode,
    @JsonProperty("rstMsg")
    String message
) {
}
