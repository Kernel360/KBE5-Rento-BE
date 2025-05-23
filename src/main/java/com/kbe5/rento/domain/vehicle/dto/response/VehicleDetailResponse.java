package com.kbe5.rento.domain.vehicle.dto.response;

import com.kbe5.rento.domain.vehicle.entity.FuelType;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.entity.VehicleType;

public record VehicleDetailResponse(
        String vehicleNumber,
        String brand,
        String modelName,
        VehicleType vehicleType,
        FuelType fuelType,
        Long totalDistanceKm,
        String batteryVoltage
) {
    public VehicleDetailResponse(String vehicleNumber, String brand,
                                 String modelName, VehicleType vehicleType, FuelType fuelType,
                                 Long totalDistanceKm, String batteryVoltage) {
        this.vehicleNumber = vehicleNumber;
        this.brand = brand;
        this.modelName = modelName;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.totalDistanceKm = totalDistanceKm;
        this.batteryVoltage = batteryVoltage;
    }

    public static VehicleDetailResponse fromEntity(Vehicle vehicle) {
        return new VehicleDetailResponse(vehicle.getVehicleNumber(),
                vehicle.getBrand(), vehicle.getModelName(), vehicle.getVehicleType(),
                vehicle.getFuelType(), vehicle.getTotalDistanceKm(), vehicle.getBatteryVoltage());
    }

}
