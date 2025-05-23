package com.kbe5.rento.domain.vehicle.entity;


import com.kbe5.rento.common.util.BaseEntity;
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

    // todo: 연관관계 매핑 끊어보기 5.21
    /*@ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;*/
    private String companyCode;

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

    // todo: 운행 시작한지 어떻게 알지? -> 운행을 만들기

    @Builder
    public Vehicle(String companyCode, String vehicleNumber, String brand, String modelName,
                   VehicleType vehicleType, FuelType fuelType, Long totalDistanceKm, String batteryVoltage) {
        this.companyCode = companyCode;
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
