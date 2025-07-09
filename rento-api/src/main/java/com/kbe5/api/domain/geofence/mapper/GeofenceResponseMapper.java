package com.kbe5.api.domain.geofence.mapper;

import com.kbe5.api.domain.geofence.dto.response.GeofenceResponse;
import com.kbe5.domain.geofence.dto.GeofenceInfo;

import java.util.List;

public class GeofenceResponseMapper {

    public static GeofenceResponse toResponse(GeofenceInfo info) {
        return new GeofenceResponse(
                info.getName(),
                info.getLatitude(),
                info.getLongitude(),
                info.getRadius(),
                info.getDescription(),
                info.getEventType(),
                info.isActive()
        );
    }

    public static List<GeofenceResponse> toResponseList(List<GeofenceInfo> infoList) {
        return infoList.stream().map(GeofenceResponseMapper::toResponse).toList();
    }
}
