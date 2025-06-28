package com.kbe5.domain.vehicle.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VehicleInfo {
    private String vehicleNumber;
    private String brand;
    private String modelName;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    public VehicleInfo(String vehicleNumber, String brand, String modelName, VehicleType vehicleType, FuelType fuelType) {
        this.vehicleNumber = vehicleNumber;
        this.brand = brand;
        this.modelName = modelName;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
    }
}