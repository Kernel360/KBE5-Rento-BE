package com.kbe5.rento.domain.vehicle.dto.response;

import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import lombok.Builder;

@Builder
public record VehicleResponse(
        String vehicleNumber,
        String brand,
        String modelName,
        Long totalDistanceKm
) {
    public static VehicleResponse fromEntity(Vehicle vehicle) {
        return VehicleResponse.builder()
                .vehicleNumber(vehicle.getVehicleNumber())
                .brand(vehicle.getBrand())
                .modelName(vehicle.getModelName())
                .totalDistanceKm(vehicle.getTotalDistanceKm())
                .build();
    }

}
