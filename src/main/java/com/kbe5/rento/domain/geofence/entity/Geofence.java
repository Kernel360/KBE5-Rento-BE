package com.kbe5.rento.domain.geofence.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.geofence.enums.EventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "geofences")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Geofence extends BaseEntity {

    private String companyCode;

    @Column(length = 30)
    private String name;

    private long latitude;
    private long longitude;
    private int radius;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String description;
    private boolean isActive;

    @Builder
    private Geofence(String companyCode, String name, long latitude, long longitude, int radius, EventType eventType,
               String description, boolean isActive) {
        this.companyCode = companyCode;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.eventType = eventType;
        this.description = description;
        this.isActive = isActive;
    }

    public void toUpdate(Geofence newGeofence) {
        this.name = newGeofence.getName();
        this.companyCode = newGeofence.getCompanyCode();
        this.latitude = newGeofence.getLatitude();
        this.longitude = newGeofence.getLongitude();
        this.radius = newGeofence.getRadius();
        this.eventType = newGeofence.getEventType();
        this.description = newGeofence.getDescription();
        this.isActive = newGeofence.isActive();
    }
}
