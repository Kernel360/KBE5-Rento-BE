package com.kbe5.domain.vehicle.service;

import com.kbe5.domain.vehicle.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleReader {

    Vehicle getVehicle(Long id);
    void getVehicleNumber(String vehicleNumber);
    Long getVehicleWithMdn(Long mdn);
    Page<Vehicle> getVehicleListWithFilter(
            Long companyId,
            Long departmentId,
            boolean onlyFree,
            Pageable pageable
    );
    Page<Vehicle> getVehicleSearch(
            Long companyId,
            String vehicleNumber,
            Pageable pageable
    );
}
