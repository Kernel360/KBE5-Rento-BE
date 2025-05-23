package com.kbe5.rento.domain.vehicle.dto.request;


import com.kbe5.rento.common.util.annotaion.VehicleNumber;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.entity.FuelType;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;


public record VehicleAddRequest(

        @NotBlank
        @VehicleNumber
        String vehicleNumber,
        @NotBlank
        String brand,
        @NotBlank
        String modelName,
        @NotBlank
        VehicleType vehicleType,
        @NotBlank
        FuelType fuelType,
        @NotBlank
        Long totalDistanceKm,
        @NotBlank
        String batteryVoltage
) {
    public static Vehicle toEntity(Manager manager, VehicleAddRequest request) {
        return Vehicle.builder()
                .companyCode(manager.getComponyCode())
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
