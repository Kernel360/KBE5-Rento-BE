package com.kbe5.api.domain.vehicle.service;

import com.kbe5.api.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.api.domain.vehicle.vo.VehicleUpdateVO;
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

    public void addVehicle(Vehicle vehicle, Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND)
        );

        var existenceVehicle = vehicleRepository.findByInfo_VehicleNumber(vehicle.getInfo().getVehicleNumber());

        if(existenceVehicle.isEmpty()) {
            throw new DomainException(ErrorType.SAME_VEHICLE_NUMBER);
        }

        vehicle.addDepartment(department);
        var res = vehicleRepository.save(vehicle);
        res.addMdn(res.getId());
    }

    public Page<Vehicle> getVehicleList(
            Manager manager,
            Long departmentId,
            boolean onlyFree,
            Pageable pageable
    ) {
        Long companyId = manager.getCompany().getId();

        if (departmentId == null && !onlyFree) {
            return vehicleRepository.findAllByCompanyId(companyId, pageable);
        }
        else if (departmentId != null && !onlyFree) {
            return vehicleRepository.findAllByCompanyIdAndDepartmentId(companyId, departmentId, pageable);
        }
        else if (departmentId == null && onlyFree) {
            return vehicleRepository.findFreeByCompanyId(companyId, VehicleStatus.RESERVATION ,pageable);
        }
        else {
            return vehicleRepository.findFreeByCompanyIdAndDepartmentId(companyId, departmentId, VehicleStatus.RESERVATION, pageable);
        }
    }

    public Page<Vehicle> searchVehicle(Manager manager, String vehicleNumber, Pageable pageable) {
        Long companyId = manager.getCompany().getId();

        return vehicleRepository.findVehicleByCompanyIdAndInfo_VehicleNumber(companyId,
                vehicleNumber, pageable);
    }


    public Vehicle getVehicle(Long vehicleId){
        return vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new DomainException(ErrorType.VEHICLE_NOT_FOUND) );
    }

    public void updateVehicle(Long vehicleId, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new DomainException(ErrorType.VEHICLE_NOT_FOUND));

        VehicleUpdateVO vo = new VehicleUpdateVO(request);
        vo.update(vehicle);
    }

    public void deleteVehicle(Long vehicleId){
        vehicleRepository.deleteById(vehicleId);
    }


    // todo: 자동차 번호로 검색 기능 -> 해당 업체의 자동차가 아니면 검색으로 안나와야함 5.23
}