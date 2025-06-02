package com.kbe5.rento.domain.geofence.dto.request;

import com.kbe5.rento.domain.geofence.entity.Geofence;
import com.kbe5.rento.domain.geofence.enums.EventType;
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
        String description,
        boolean isActive
) {
    public static Geofence toEntity(GeofenceUpdateRequest request) {
        return Geofence.builder()
                .companyCode(request.companyCode)
                .name(request.name)
                .latitude(request.latitude)
                .longitude(request.longitude)
                .radius(request.radius)
                .eventType(EventType.OFF)
                .description(request.description)
                .isActive(request.isActive)
                .isActive(false)
                .build();
    }
}
