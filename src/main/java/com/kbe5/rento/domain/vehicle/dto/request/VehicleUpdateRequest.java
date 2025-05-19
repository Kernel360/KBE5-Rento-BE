package com.kbe5.rento.domain.vehicle.dto.request;

public record VehicleUpdateRequest(
        String vehicleNumber,
        Long totalDistanceKm,
        String batteryVoltage
) {
}
