package com.kbe5.api.domain.vehicle.service;

import com.kbe5.api.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.repository.DepartmentRepository;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.entity.VehicleStatus;
import com.kbe5.domain.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DepartmentRepository departmentRepository;

    public Vehicle addVehicle(Vehicle vehicle, Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND)
        );
        vehicle.addDepartment(department);
        var res = vehicleRepository.save(vehicle);
        res.addMdn(res.getId());
        return res;
    }

    public Page<Vehicle> getVehicleList(
            Manager manager,
            Long departmentId,
            boolean onlyFree,
            Pageable pageable
    ) {
        Long companyInd = manager.getCompany().getId();

        if (departmentId == null && !onlyFree) {
            return vehicleRepository.findAllByCompanyId(companyInd, pageable);
        }
        else if (departmentId != null && !onlyFree) {
            return vehicleRepository.findAllByCompanyIdAndDepartmentId(companyInd, departmentId, pageable);
        }
        else if (departmentId == null && onlyFree) {
            return vehicleRepository.findFreeByCompanyId(companyInd, VehicleStatus.RESERVATION ,pageable);
        }
        else {
            return vehicleRepository.findFreeByCompanyIdAndDepartmentId(companyInd, departmentId, VehicleStatus.RESERVATION, pageable);
        }
    }

    public Vehicle getVehicle(Long vehicleId){
        return vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new DomainException(ErrorType.VEHICLE_NOT_FOUND) );
    }

    public void updateVehicle(Long vehicleId, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new DomainException(ErrorType.VEHICLE_NOT_FOUND));

        vehicle.update(request);
    }

    public void deleteVehicle(Long vehicleId){
        vehicleRepository.deleteById(vehicleId);
    }


    // todo: 자동차 번호로 검색 기능 -> 해당 업체의 자동차가 아니면 검색으로 안나와야함 5.23
}