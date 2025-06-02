package com.kbe5.rento.domain.geofence.dto.request;

import com.kbe5.rento.domain.geofence.entity.Geofence;
import com.kbe5.rento.domain.geofence.enums.EventType;
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

        @NotBlank
        long longitude,

        @NotNull
        int radius,

        @NotBlank
        @Enumerated(EnumType.STRING)
        EventType eventType,
        String description,
        boolean isActive
) {

}
