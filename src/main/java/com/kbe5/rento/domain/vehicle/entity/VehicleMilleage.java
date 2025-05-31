package com.kbe5.rento.domain.vehicle.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VehicleMilleage {

    private long totalDistanceKm;
    private String batteryVoltage;

    public VehicleMilleage(Long totalDistanceKm, String batteryVoltage) {
        this.totalDistanceKm = totalDistanceKm;
        this.batteryVoltage = batteryVoltage;
    }
}