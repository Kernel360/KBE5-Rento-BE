package com.kbe5.api.domain.geofence.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GeofenceRegisterRequest(
        @NotBlank
        String companyCode,

        @NotBlank
        String name,

        @NotNull
        long latitude,

        @NotNull
        long longitude,

        @NotNull
        int radius,
        String description
) {
}
