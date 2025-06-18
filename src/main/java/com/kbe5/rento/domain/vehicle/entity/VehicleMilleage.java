package com.kbe5.rento.domain.vehicle.entity;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VehicleMilleage {

    private Long mdn;
    private Long totalDistanceKm;
    private String batteryVoltage;

    public VehicleMilleage(Long totalDistanceKm, String batteryVoltage) {
        this.totalDistanceKm = totalDistanceKm;
        this.batteryVoltage = batteryVoltage;
    }

    public void addMdn(Long vehicleId) {
        this.mdn = vehicleId;
    }

    public void addDistance(Long currentDistance) {
        if (currentDistance == null || currentDistance < 0) {
            throw new DomainException(ErrorType.DRIVE_NOT_DISTANCE);
        }
        this.totalDistanceKm = currentDistance;
    }
}
