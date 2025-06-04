package com.kbe5.rento.domain.geofence.dto.response;

import com.kbe5.rento.domain.geofence.entity.Geofence;
import com.kbe5.rento.domain.geofence.enums.EventType;

import java.util.List;

public record GeofenceInfoResponse(
        String name,
        long latitude,
        long longitude,
        int radius,
        String description,
        EventType eventType,
        boolean isActive
) {
    public static GeofenceInfoResponse fromEntity(Geofence geofence) {
        return new GeofenceInfoResponse(geofence.getName(), geofence.getLatitude(), geofence.getLongitude(),
                geofence.getRadius(), geofence.getDescription(), geofence.getEventType(), geofence.isActive());
    }

    public static List<GeofenceInfoResponse> fromEntity(List<Geofence> geofenceList) {
        return geofenceList.stream().map(GeofenceInfoResponse::fromEntity).toList();
    }
}
