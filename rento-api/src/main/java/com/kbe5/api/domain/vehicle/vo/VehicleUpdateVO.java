package com.kbe5.api.domain.vehicle.vo;

import com.kbe5.api.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.domain.vehicle.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class VehicleUpdateVO {

    private final Long totalDistanceKm;

    private final String batteryVoltage;

    public VehicleUpdateVO(VehicleUpdateRequest request) {
        this.totalDistanceKm = request.totalDistanceKm();
        this.batteryVoltage = request.batteryVoltage();
    }

    public void update(Vehicle vehicle) {
        vehicle.update(totalDistanceKm, batteryVoltage);
    }
}
