package com.kbe5.rento.domain.device.dto.resonse;

import com.kbe5.rento.domain.device.entity.GeofenceControlInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GeofenceControlInfoResponse(
        long geoControlId,
        int updateValue,
        int geofenceGroupId,
        short geoEventType,
        double geofenceRange,
        BigDecimal latitude,
        BigDecimal longitude,
        LocalDateTime onTime,
        LocalDateTime offTime,
        short storeType
) {
    public static GeofenceControlInfoResponse fromEntity(GeofenceControlInfo entity) {
        return new GeofenceControlInfoResponse(
                entity.getGeoCtrId(),
                entity.getUpVal(),
                entity.getGeoGrpId(),
                entity.getGeoEvtTp(),
                entity.getGeoRange(),
                entity.getLat(),
                entity.getLon(),
                entity.getOnTime(),
                entity.getOffTime(),
                entity.getStoreTp()
        );
    }
}
