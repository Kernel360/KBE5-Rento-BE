package com.kbe5.rento.domain.device.dto.resonse;

import com.kbe5.rento.domain.device.domain.GeofenceControlInfo;
import com.kbe5.rento.domain.device.enums.GeoEventType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GeofenceControlInfoResponse(
        long geoControlId,
        int updateValue,
        int geofenceGroupId,
        int geoEventType,
        int geofenceRange,
        BigDecimal latitude,
        BigDecimal longitude,
        LocalDateTime onTime,
        LocalDateTime offTime,
        short storeType
) {

    public static GeofenceControlInfoResponse fromDomain(GeofenceControlInfo domain) {
        return new GeofenceControlInfoResponse(
                domain.getGeoControlId(),
                domain.getUpdateValue(),
                domain.getGeofenceGroupId(),
                domain.getGeoEventType(),
                domain.getGeofenceRange(),
                domain.getLatitude(),
                domain.getLongitude(),
                domain.getOnTime(),
                domain.getOffTime(),
                domain.getStoreType()
        );
    }
}
