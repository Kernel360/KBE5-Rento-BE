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
                .vehicleNumber(vehicle.getInfo().getVehicleNumber())
                .brand(vehicle.getInfo().getBrand())
                .modelName(vehicle.getInfo().getModelName())
                .vehicleType(vehicle.getInfo().getVehicleType())
                .fuelType(vehicle.getInfo().getFuelType())
                .vehicleStatus(vehicle.getStatus())
                .totalDistanceKm(vehicle.getMileage().getTotalDistanceKm())
                .batteryVoltage(vehicle.getMileage().getBatteryVoltage())
                .build();
    }
}
