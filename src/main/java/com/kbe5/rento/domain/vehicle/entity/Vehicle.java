package com.kbe5.rento.domain.vehicle.entity;


import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="vehicles")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Vehicle extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // todo: 부서 추가 5.25

    private String vehicleNumber;
    private String brand;
    private String modelName;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    private Long totalDistanceKm;

    // todo: 이번달 운행 누적 거리 필요한가?
    // private Long monthTotalKm;

    private String batteryVoltage;

    @Builder
    private Vehicle(Company company, String vehicleNumber, String brand, String modelName,
                   VehicleType vehicleType, FuelType fuelType, Long totalDistanceKm, String batteryVoltage) {
        this.company = company;
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
