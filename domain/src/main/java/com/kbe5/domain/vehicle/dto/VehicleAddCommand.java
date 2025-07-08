package com.kbe5.domain.vehicle.dto;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.vehicle.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class VehicleAddCommand {
    private Long departmentId;
    private String vehicleNumber;
    private String brand;
    private String modelName;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private Long totalDistanceKm;
    private String batteryVoltage;

    public Vehicle toEntity(Company company) {
        VehicleInformation info = new VehicleInformation(vehicleNumber, brand, modelName, vehicleType, fuelType);
        VehicleMilleage milleage = new VehicleMilleage(totalDistanceKm, batteryVoltage);
        return Vehicle.of(company, info, milleage);
    }
}
