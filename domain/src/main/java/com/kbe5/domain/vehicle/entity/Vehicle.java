package com.kbe5.domain.vehicle.entity;

import com.kbe5.domain.BaseEntity;
import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.department.entity.Department;
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

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Embedded
    private VehicleInfo info;

    @Embedded
    private VehicleMilleage mileage;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @Builder
    public Vehicle(Company company, VehicleInfo info, VehicleMilleage mileage) {
        this.company = company;
        this.info = info;
        this.mileage = mileage;
        this.status = VehicleStatus.READY;
    }

    public void addDepartment(Department department) {
        this.department = department;
    }

    public static Vehicle of(Company company, VehicleInfo info, VehicleMilleage mileage) {
        return new Vehicle(company, info, mileage);
    }

    public void update(long totalDistanceKm, String batteryVoltage) {
        this.mileage = new VehicleMilleage(totalDistanceKm, batteryVoltage);
    }

    public void addMdn(Long vehicleId) {
        this.mileage.addMdn(vehicleId);
    }

    public void addDistance(Long distance) {
        this.mileage.addDistance(distance);
    }

    // todo: 시간대 별로 예약 가능 여부는 잠깐 빼두기 필요하다면 그냥 상태 말고 시간대 별로 valid걸면 될듯 6.17
    public void reservation() {
        this.status = VehicleStatus.RESERVATION;
    }

    public void cancel() {
        this.status = VehicleStatus.READY;
    }
}
