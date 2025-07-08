package com.kbe5.domain.vehicle.entity;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.department.entity.Department;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleInfo {

    private Long id;
    private Company company;
    private Department department;
    private VehicleInformation information;
    private VehicleMilleage mileage;
    private VehicleStatus status;

    public static VehicleInfo fromEntity(Vehicle vehicle) {
        return VehicleInfo.builder()
                .company(vehicle.getCompany())
                .department(vehicle.getDepartment())
                .information(vehicle.getInformation())
                .status(vehicle.getStatus())
                .build();
    }
}
