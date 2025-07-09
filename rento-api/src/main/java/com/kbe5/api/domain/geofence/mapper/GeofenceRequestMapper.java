package com.kbe5.api.domain.geofence.mapper;

import com.kbe5.api.domain.geofence.dto.request.GeofenceRegisterRequest;
import com.kbe5.api.domain.geofence.dto.request.GeofenceUpdateRequest;
import com.kbe5.domain.geofence.dto.GeofenceCommand;

public class GeofenceRequestMapper {

    public static GeofenceCommand.Register toCommand(GeofenceRegisterRequest request) {
        return GeofenceCommand.Register.builder()
                .name(request.name())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .radius(request.radius())
                .description(request.description())
                .companyCode(request.companyCode())
                .build();
    }

    public static GeofenceCommand.Update toCommand(GeofenceUpdateRequest request) {
        return GeofenceCommand.Update.builder()
                .name(request.name())
                .eventType(request.eventType())
                .isActive(request.isActive())
                .radius(request.radius())
                .companyCode(request.companyCode())
                .longitude(request.longitude())
                .latitude(request.latitude())
                .description(request.description())
                .build();
    }
}
