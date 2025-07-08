package com.kbe5.domain.vehicle.service;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.service.DepartmentReader;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.vehicle.dto.VehicleAddCommand;
import com.kbe5.domain.vehicle.dto.VehicleUpdateCommand;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.entity.VehicleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


// todo: 반환을 info로 하는 것에 대해 고민해보기 7.8
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleStore vehicleStore;
    private final VehicleReader vehicleReader;
    private final DepartmentReader departmentReader;

    @Override
    public void addVehicle(VehicleAddCommand command, Long departmentId, Company company) {
        Department department = departmentReader.getDepartmentById(departmentId);

        Vehicle vehicle = vehicleReader.getVehicleNumber(command.getVehicleNumber());

        vehicle.addDepartment(department);
        Vehicle initVehicle = vehicleStore.addVehicle(command, company);
        initVehicle.addMdn(initVehicle.getId());
    }

    @Override
    public Page<VehicleInfo> getVehicleList(Manager manager, Long departmentId, boolean onlyFree, Pageable pageable) {

        Page<Vehicle> vehicleList =  vehicleReader.getVehicleListWithFilter(
                manager.getCompany().getId(),
                departmentId,
                onlyFree,
                pageable
        );

        return vehicleList.map(VehicleInfo::fromEntity);
    }

    @Override
    public Page<VehicleInfo> searchVehicle(Manager manager, String vehicleNumber, Pageable pageable) {
        Page<Vehicle> vehicleList = vehicleReader.getVehicleSearch(
                manager.getCompany().getId(),
                vehicleNumber,
                pageable
        );

        return vehicleList.map(VehicleInfo::fromEntity);
    }


    @Override
    public VehicleInfo getVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleReader.getVehicle(vehicleId);
        return VehicleInfo.fromEntity(vehicle);
    }

    @Override
    public void updateVehicle(Long vehicleId, VehicleUpdateCommand command) {
        Vehicle vehicle = vehicleReader.getVehicle(vehicleId);
        vehicle.update(command.getTotalDistanceKm(), command.getBatteryVoltage());
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleReader.getVehicle(vehicleId);
        vehicle.delete();
    }

    @Override
    public Long getCompanyIdByMdn(Long mdn) {
        return vehicleReader.getVehicleWithMdn(mdn);
    }
}
