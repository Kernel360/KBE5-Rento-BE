package com.kbe5.rento.domain.vehicle.dto.response;

import com.kbe5.rento.domain.vehicle.entity.FuelType;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.entity.VehicleType;
import lombok.Builder;

@Builder
public record VehicleDetailResponse(
        String vehicleNumber,
        String brand,
        String modelName,
        VehicleType vehicleType,
        FuelType fuelType,
        Long totalDistanceKm,
        String batteryVoltage
) {
    public static VehicleDetailResponse fromEntity(Vehicle vehicle) {
        return VehicleDetailResponse.builder()
                .vehicleNumber(vehicle.getVehicleNumber())
                .brand(vehicle.getBrand())
                .modelName(vehicle.getModelName())
                .vehicleType(vehicle.getVehicleType())
                .fuelType(vehicle.getFuelType())
                .totalDistanceKm(vehicle.getTotalDistanceKm())
                .batteryVoltage(vehicle.getBatteryVoltage())
                .build();
    }
}
