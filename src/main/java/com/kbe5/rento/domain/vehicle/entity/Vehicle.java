package com.kbe5.rento.domain.vehicle.entity;


import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.department.entity.Department;
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

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Embedded
    private VehicleInfo info;

    @Embedded
    private VehicleMilleage mileage;

    // todo: 이번달 운행 누적 거리 필요한가?
    // private Long monthTotalKm;

    @Builder
    public Vehicle(Company company, VehicleInfo info, VehicleMilleage mileage) {
        this.company = company;
        this.info = info;
        this.mileage = mileage;
    }

    public void addDepartment(Department department) {
        this.department = department;
    }

    public static Vehicle of(Company company, VehicleInfo info, VehicleMilleage mileage) {
        return new Vehicle(company, info, mileage);
    }

    public void update(VehicleUpdateRequest request) {
        this.mileage = new VehicleMilleage(request.totalDistanceKm(), request.batteryVoltage());
    }

    public void addMdn(Long vehicleId) {
        this.mileage.addMdn(vehicleId);
    }

    public void addDistance(Long distance) {
        this.mileage.addDistance(distance);
    }
}
