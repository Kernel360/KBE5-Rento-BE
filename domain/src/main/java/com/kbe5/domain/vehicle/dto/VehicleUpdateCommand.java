package com.kbe5.domain.vehicle.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleUpdateCommand {
    Long totalDistanceKm;
    String batteryVoltage;

    public VehicleUpdateCommand(Long totalDistanceKm, String batteryVoltage) {
        this.totalDistanceKm = totalDistanceKm;
        this.batteryVoltage = batteryVoltage;
    }
}
