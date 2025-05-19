package com.kbe5.rento.domain.vehicle.entity;


import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.manager.entity.Department;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Vehicle extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String vehicleNumber;
    private String brand;
    private String modelName;

    @Enumerated(EnumType.STRING)
    private String vehicleType;

    @Enumerated(EnumType.STRING)
    private String fuelType;

    private Long totalDistanceKm;
    // todo: 이번달 운행 누적 거리 필요한가?
    // private Long monthTotalKm;
    private String batteryVoltage;

    @Builder
    public Vehicle(String vehicleNumber, String brand, String modelName, String vehicleType,
                   String fuelType, Long totalDistanceKm, String batteryVoltage) {
        this.vehicleNumber = vehicleNumber;
        this.brand = brand;
        this.modelName = modelName;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.totalDistanceKm = totalDistanceKm;
        this.batteryVoltage = batteryVoltage;
    }

    public void update(VehicleUpdateRequest request){
        this.vehicleNumber = request.vehicleNumber();
        this.totalDistanceKm = request.totalDistanceKm();
        this.batteryVoltage = request.batteryVoltage();
    }
}
