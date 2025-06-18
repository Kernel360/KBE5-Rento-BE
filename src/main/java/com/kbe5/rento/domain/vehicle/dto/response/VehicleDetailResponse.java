package com.kbe5.rento.domain.vehicle.dto.response;

import com.kbe5.rento.domain.vehicle.entity.FuelType;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.entity.VehicleStatus;
import com.kbe5.rento.domain.vehicle.entity.VehicleType;
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
                .vehicleNumber(vehicle.getInfo().vehicleNumber())
                .brand(vehicle.getInfo().brand())
                .modelName(vehicle.getInfo().modelName())
                .vehicleType(vehicle.getInfo().vehicleType())
                .fuelType(vehicle.getInfo().fuelType())
                .vehicleStatus(vehicle.getStatus())
                .totalDistanceKm(vehicle.getMileage().getTotalDistanceKm())
                .batteryVoltage(vehicle.getMileage().getBatteryVoltage())
                .build();
    }
}
