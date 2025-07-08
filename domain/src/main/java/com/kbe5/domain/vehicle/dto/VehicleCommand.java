package com.kbe5.domain.vehicle.dto;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.entity.VehicleInformation;
import com.kbe5.domain.vehicle.entity.VehicleMilleage;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class VehicleCommand {

    private Company company;
    private VehicleInformation info;
    private VehicleMilleage mileage;

    public Vehicle toEntity() {
        return Vehicle.builder()
                .company(company)
                .info(info)
                .mileage(mileage)
                .build();
    }
}
