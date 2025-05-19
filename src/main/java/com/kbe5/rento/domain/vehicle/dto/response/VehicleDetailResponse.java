package com.kbe5.rento.domain.vehicle.dto.response;

import com.kbe5.rento.domain.manager.entity.Department;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;

public record VehicleDetailResponse(
        Department department,
        String vehicleNumber,
        String brand,
        String modelName,
        String vehicleType,
        String fuelType,
        Long totalDistanceKm,
        String batteryVoltage
) {
    public VehicleDetailResponse(Department department, String vehicleNumber, String brand,
                                 String modelName, String vehicleType, String fuelType,
                                 Long totalDistanceKm, String batteryVoltage) {
        this.department = department;
        this.vehicleNumber = vehicleNumber;
        this.brand = brand;
        this.modelName = modelName;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.totalDistanceKm = totalDistanceKm;
        this.batteryVoltage = batteryVoltage;
    }

    public static VehicleDetailResponse fromEntity(Vehicle vehicle) {
        return new VehicleDetailResponse(vehicle.getDepartment(), vehicle.getVehicleNumber(),
                vehicle.getBrand(), vehicle.getModelName(), vehicle.getVehicleType(),
                vehicle.getFuelType(), vehicle.getTotalDistanceKm(), vehicle.getBatteryVoltage());
    }

}
