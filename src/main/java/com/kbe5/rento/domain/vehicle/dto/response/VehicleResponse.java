package com.kbe5.rento.domain.vehicle.dto.response;

import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import lombok.Builder;

@Builder
public record VehicleResponse(
        Long id,
        String vehicleNumber,
        String brand,
        String modelName,
        Long totalDistanceKm
) {
    public static VehicleResponse fromEntity(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .vehicleNumber(vehicle.getInfo().vehicleNumber())
                .brand(vehicle.getInfo().brand())
                .modelName(vehicle.getInfo().modelName())
                .totalDistanceKm(vehicle.getMileage().getTotalDistanceKm())
                .build();
    }

}
