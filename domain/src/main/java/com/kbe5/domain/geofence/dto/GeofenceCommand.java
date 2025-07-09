package com.kbe5.domain.geofence.dto;

import com.kbe5.domain.geofence.entity.Geofence;
import com.kbe5.domain.geofence.enums.EventType;
import lombok.Builder;
import lombok.Getter;

public class GeofenceCommand {

    @Getter
    @Builder
    public static class Register {
        private String companyCode;
        private String name;
        private long latitude;
        private long longitude;
        private int radius;
        private EventType eventType;
        private String description;
        private boolean isActive;

        public Geofence toEntity() {
            return Geofence.builder()
                    .companyCode(companyCode)
                    .name(name)
                    .latitude(latitude)
                    .longitude(longitude)
                    .radius(radius)
                    .eventType(eventType)
                    .description(description)
                    .isActive(isActive)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Update {
        private String companyCode;
        private String name;
        private long latitude;
        private long longitude;
        private int radius;
        private EventType eventType;
        private String description;
        private boolean isActive;

        public void applyTo(Geofence geofence) {
            geofence.toUpdate(name, companyCode, latitude, longitude, radius, eventType, description, isActive);
        }
    }
}
