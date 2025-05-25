package com.kbe5.rento.domain.vehicle.dto.request;

import com.kbe5.rento.common.util.annotaion.VehicleNumber;

public record VehicleUpdateRequest(
        @VehicleNumber
        String vehicleNumber,
        Long totalDistanceKm,
        String batteryVoltage
) {
}
