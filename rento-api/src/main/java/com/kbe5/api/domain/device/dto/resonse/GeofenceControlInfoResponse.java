package com.kbe5.api.domain.device.dto.resonse;


import com.kbe5.domain.device.entity.GeofenceControlInfo;

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
}
