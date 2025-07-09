package com.kbe5.domain.vehicle.dto;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.entity.VehicleInformation;
import com.kbe5.domain.vehicle.entity.VehicleMilleage;
import com.kbe5.domain.vehicle.entity.VehicleStatus;
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
                .id(vehicle.getId())
                .company(vehicle.getCompany())
                .department(vehicle.getDepartment())
                .information(vehicle.getInformation())
                .mileage(vehicle.getMileage())
                .status(vehicle.getStatus())
                .build();
    }
}
