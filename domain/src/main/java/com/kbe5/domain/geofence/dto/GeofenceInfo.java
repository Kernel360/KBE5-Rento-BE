package com.kbe5.domain.geofence.dto;


import com.kbe5.domain.geofence.entity.Geofence;
import com.kbe5.domain.geofence.enums.EventType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeofenceInfo {

    private Long id;
    private String companyCode;
    private String name;
    private long latitude;
    private long longitude;
    private int radius;
    private EventType eventType;
    private String description;
    private boolean isActive;

    public static GeofenceInfo fromEntity(Geofence geofence) {
        return GeofenceInfo.builder()
                .id(geofence.getId())
                .companyCode(geofence.getCompanyCode())
                .name(geofence.getName())
                .latitude(geofence.getLatitude())
                .longitude(geofence.getLongitude())
                .radius(geofence.getRadius())
                .eventType(geofence.getEventType())
                .description(geofence.getDescription())
                .isActive(geofence.isActive())
                .build();
    }
}
