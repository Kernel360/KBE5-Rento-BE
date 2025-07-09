package com.kbe5.api.domain.geofence.dto.request;

import com.kbe5.domain.geofence.enums.EventType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GeofenceUpdateRequest(
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

        @NotNull
        @Enumerated(EnumType.STRING)
        EventType eventType,
        String description,
        boolean isActive
) {
}
