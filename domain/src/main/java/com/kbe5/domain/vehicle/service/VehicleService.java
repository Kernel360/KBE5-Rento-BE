package com.kbe5.domain.vehicle.service;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.vehicle.dto.VehicleAddCommand;
import com.kbe5.domain.vehicle.dto.VehicleUpdateCommand;
import com.kbe5.domain.vehicle.entity.VehicleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {

    void addVehicle(VehicleAddCommand command, Long departmentId, Company company);
    Page<VehicleInfo> getVehicleList(
            Manager manager,
            Long departmentId,
            boolean onlyFree,
            Pageable pageable
    );
    Page<VehicleInfo> searchVehicle(
            Manager manager,
            String vehicleNumber,
            Pageable pageable
    );
    VehicleInfo getVehicle(Long vehicleId);
    void updateVehicle(Long vehicleId, VehicleUpdateCommand command);
    void deleteVehicle(Long vehicleId);
    Long getCompanyIdByMdn(Long mdn);
}
