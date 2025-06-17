package com.kbe5.rento.domain.vehicle.dto.response;

import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.entity.VehicleStatus;
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
                .vehicleNumber(vehicle.getInfo().vehicleNumber())
                .brand(vehicle.getInfo().brand())
                .modelName(vehicle.getInfo().modelName())
                .status(vehicle.getStatus())
                .totalDistanceKm(vehicle.getMileage().getTotalDistanceKm())
                .build();
    }

}
