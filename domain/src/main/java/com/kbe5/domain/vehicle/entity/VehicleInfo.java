package com.kbe5.domain.vehicle.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record VehicleInfo(
        String vehicleNumber,
        String brand,
        String modelName,
        @Enumerated(EnumType.STRING) VehicleType vehicleType,
        @Enumerated(EnumType.STRING) FuelType fuelType
){
}
