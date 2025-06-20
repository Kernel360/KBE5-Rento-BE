package com.kbe5.domain.geofence.entity;

import com.kbe5.domain.BaseEntity;
import com.kbe5.domain.geofence.enums.EventType;
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

    public void toUpdate(GeofenceUpdateRequest request) {
        this.name = request.name();
        this.companyCode = request.companyCode();
        this.latitude = request.latitude();
        this.longitude = request.longitude();
        this.radius = request.radius();
        this.eventType = request.eventType();
        this.description = request.description();
        this.isActive = request.isActive();
    }
}
