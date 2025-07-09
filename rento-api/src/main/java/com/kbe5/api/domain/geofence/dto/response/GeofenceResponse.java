package com.kbe5.api.domain.geofence.dto.response;

import com.kbe5.domain.geofence.enums.EventType;

public record GeofenceResponse(
        String name,
        long latitude,
        long longitude,
        int radius,
        String description,
        EventType eventType,
        boolean isActive
) {
}
