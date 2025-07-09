package com.kbe5.domain.vehicle.dto;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.vehicle.entity.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleAddCommand {
    private Long departmentId;
    private String vehicleNumber;
    private String brand;
    private String modelName;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private Long totalDistanceKm;
    private String batteryVoltage;

    public VehicleAddCommand(
            Long departmentId,
            String vehicleNumber,
            String brand,
            String modelName,
            VehicleType vehicleType,
            FuelType fuelType,
            Long totalDistanceKm,
            String batteryVoltage) {
        this.departmentId = departmentId;
        this.vehicleNumber = vehicleNumber;
        this.brand = brand;
        this.modelName = modelName;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.totalDistanceKm = totalDistanceKm;
        this.batteryVoltage = batteryVoltage;
    }

    public Vehicle toEntity(Company company) {
        VehicleInformation info = new VehicleInformation(vehicleNumber, brand, modelName, vehicleType, fuelType);
        VehicleMilleage milleage = new VehicleMilleage(totalDistanceKm, batteryVoltage);
        return Vehicle.of(company, info, milleage);
    }

}
