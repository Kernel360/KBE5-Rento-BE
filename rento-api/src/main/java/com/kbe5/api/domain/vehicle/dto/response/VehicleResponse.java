package com.kbe5.api.domain.vehicle.dto.response;

import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.entity.VehicleStatus;
import lombok.Builder;

@Builder
public record VehicleResponse(
        Long id,
        String vehicleNumber,
        String brand,
        String modelName,
        VehicleStatus status,
        Long totalDistanceKm
) {
    public static VehicleResponse fromEntity(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .vehicleNumber(vehicle.getInfo().getVehicleNumber())
                .brand(vehicle.getInfo().getBrand())
                .modelName(vehicle.getInfo().getModelName())
                .status(vehicle.getStatus())
                .totalDistanceKm(vehicle.getMileage().getTotalDistanceKm())
                .build();
    }

}
