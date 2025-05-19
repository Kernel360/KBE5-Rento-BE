package com.kbe5.rento.domain.vehicle.dto.request;


import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;

public record VehicleAddRequest(
        @NotBlank
        String vehicleNumber,
        @NotBlank
        String brand,
        @NotBlank
        String modelName,
        @NotBlank
        String vehicleType,
        @NotBlank
        String fuelType,
        @NotBlank
        Long totalDistanceKm,
        @NotBlank
        String batteryVoltage
) {
    public static Vehicle toEntity(VehicleAddRequest request) {

        return Vehicle.builder()
                .vehicleNumber(request.vehicleNumber)
                .brand(request.brand)
                .modelName(request.modelName)
                .vehicleType(request.vehicleType)
                .fuelType(request.fuelType)
                .totalDistanceKm(request.totalDistanceKm)
                .batteryVoltage(request.batteryVoltage)
                .build();
    }
}
