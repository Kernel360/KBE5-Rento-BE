package com.kbe5.api.domain.geofence.vo;


import com.kbe5.api.domain.geofence.dto.request.GeofenceUpdateRequest;
import com.kbe5.domain.company.entity.Company;

import com.kbe5.domain.geofence.entity.Geofence;
import com.kbe5.domain.geofence.enums.EventType;
import lombok.Getter;

@Getter
public class GeofenceUpdateVO {

    private final String companyCode;

    private final String name;

    private final long latitude;

    private final long longitude;

    private final int radius;

    private final EventType eventType;

    private final String description;

    private final boolean isActive;

    public GeofenceUpdateVO(GeofenceUpdateRequest request) {
        this.companyCode = request.companyCode();
        this.name = request.name();
        this.latitude = request.latitude();
        this.longitude = request.longitude();
        this.radius = request.radius();
        this.eventType = request.eventType();
        this.description = request.description();
        this.isActive = request.isActive();

    }

    public void update(Geofence geofence) {
        geofence.toUpdate(companyCode, name, latitude, longitude, radius, eventType, description, isActive);
    }
}
