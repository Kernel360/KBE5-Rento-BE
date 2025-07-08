package com.kbe5.api.domain.vehicle.mapper;

import com.kbe5.api.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.api.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.domain.vehicle.entity.VehicleInfo;
import org.springframework.stereotype.Component;

@Component
public class VehicleResponseMapper {

    public VehicleResponse toVehicleResponse(VehicleInfo info) {
        return VehicleResponse.builder()
                .id(info.getId())
                .vehicleNumber(info.getInformation().getVehicleNumber())
                .brand(info.getInformation().getBrand())
                .modelName(info.getInformation().getModelName())
                .status(info.getStatus())
                .totalDistanceKm(info.getMileage().getTotalDistanceKm())
                .build();
    }

    public VehicleDetailResponse toVehicleDetailResponse(VehicleInfo info) {
        return VehicleDetailResponse.builder()
                .vehicleNumber(info.getInformation().getVehicleNumber())
                .brand(info.getInformation().getBrand())
                .modelName(info.getInformation().getModelName())
                .vehicleType(info.getInformation().getVehicleType())
                .fuelType(info.getInformation().getFuelType())
                .vehicleStatus(info.getStatus())
                .totalDistanceKm(info.getMileage().getTotalDistanceKm())
                .batteryVoltage(info.getMileage().getBatteryVoltage())
                .build();
    }
}
