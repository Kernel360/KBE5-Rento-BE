package com.kbe5.domain.vehicle.service;

import com.kbe5.domain.vehicle.dto.VehicleAddCommand;
import com.kbe5.domain.vehicle.dto.VehicleUpdateCommand;
import com.kbe5.domain.vehicle.dto.VehicleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {

    void addVehicle(VehicleAddCommand command, Long departmentId, Long companyId);
    Page<VehicleInfo> getVehicleList(
            Long companyId,
            Long departmentId,
            boolean onlyFree,
            Pageable pageable
    );
    Page<VehicleInfo> searchVehicle(
            Long companyId,
            String vehicleNumber,
            Pageable pageable
    );
    VehicleInfo getVehicle(Long vehicleId);
    void updateVehicle(Long vehicleId, VehicleUpdateCommand command);
    void deleteVehicle(Long vehicleId);
    Long getCompanyIdByMdn(Long mdn);
}
