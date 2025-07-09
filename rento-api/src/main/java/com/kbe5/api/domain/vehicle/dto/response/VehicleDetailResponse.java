package com.kbe5.api.domain.vehicle.dto.response;

import com.kbe5.domain.vehicle.entity.FuelType;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.entity.VehicleStatus;
import com.kbe5.domain.vehicle.entity.VehicleType;
import lombok.Builder;

@Builder
public record VehicleDetailResponse(
        String vehicleNumber,
        String brand,
        String modelName,
        VehicleType vehicleType,
        FuelType fuelType,
        VehicleStatus vehicleStatus,
        Long totalDistanceKm,
        String batteryVoltage
) {
    public static VehicleDetailResponse fromEntity(Vehicle vehicle) {
        return VehicleDetailResponse.builder()
                .vehicleNumber(vehicle.getInformation().getVehicleNumber())
                .brand(vehicle.getInformation().getBrand())
                .modelName(vehicle.getInformation().getModelName())
                .vehicleType(vehicle.getInformation().getVehicleType())
                .fuelType(vehicle.getInformation().getFuelType())
                .vehicleStatus(vehicle.getStatus())
                .totalDistanceKm(vehicle.getMileage().getTotalDistanceKm())
                .batteryVoltage(vehicle.getMileage().getBatteryVoltage())
                .build();
    }
}
