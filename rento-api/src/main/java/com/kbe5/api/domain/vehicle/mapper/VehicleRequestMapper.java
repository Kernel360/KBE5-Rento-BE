package com.kbe5.api.domain.vehicle.mapper;

import com.kbe5.api.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.api.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.domain.vehicle.dto.VehicleAddCommand;
import com.kbe5.domain.vehicle.dto.VehicleUpdateCommand;
import org.springframework.stereotype.Component;

@Component
public class VehicleRequestMapper {

    public VehicleAddCommand toAddCommand(VehicleAddRequest addRequest) {
        return VehicleAddCommand.builder()
                .departmentId(addRequest.departmentId())
                .vehicleNumber(addRequest.vehicleNumber())
                .brand(addRequest.brand())
                .modelName(addRequest.modelName())
                .vehicleType(addRequest.vehicleType())
                .fuelType(addRequest.fuelType())
                .totalDistanceKm(addRequest.totalDistanceKm())
                .batteryVoltage(addRequest.batteryVoltage())
                .build();
    }

    public VehicleUpdateCommand toUpdateCommand(VehicleUpdateRequest updateRequest) {
        return VehicleUpdateCommand.builder()
                .totalDistanceKm(updateRequest.totalDistanceKm())
                .batteryVoltage(updateRequest.batteryVoltage())
                .build();
    }
}
