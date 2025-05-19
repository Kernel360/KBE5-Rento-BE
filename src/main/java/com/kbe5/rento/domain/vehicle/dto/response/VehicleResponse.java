package com.kbe5.rento.domain.vehicle.dto.response;

import com.kbe5.rento.domain.vehicle.entity.Vehicle;

public record VehicleResponse(
        String vehicleNumber,
        String brand,
        String modelName,
        Long totalDistanceKm
) {
    public static VehicleResponse fromEntity(Vehicle vehicle) {
        return new VehicleResponse(vehicle.getVehicleNumber(), vehicle.getBrand(), vehicle.getModelName(),
                vehicle.getTotalDistanceKm());
    }

}
