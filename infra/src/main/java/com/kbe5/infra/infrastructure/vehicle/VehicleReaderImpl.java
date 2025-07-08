package com.kbe5.infra.infrastructure.vehicle;

import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.entity.VehicleStatus;
import com.kbe5.domain.vehicle.service.VehicleReader;
import com.kbe5.infra.infrastructure.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleReaderImpl implements VehicleReader {

    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle getVehicle(Long id) {
        return  vehicleRepository.findById(id).orElseThrow(
                () -> new DomainException(ErrorType.VEHICLE_NOT_FOUND)
        );
    }

    @Override
    public void getVehicleNumber(String vehicleNumber) {
        vehicleRepository.findByInformation_VehicleNumber(vehicleNumber)
                .ifPresent(v -> {
                    throw new DomainException(ErrorType.SAME_VEHICLE_NUMBER);
                });
    }

    @Override
    public Long getVehicleWithMdn(Long mdn) {
        return vehicleRepository.findByCompanyIdByMdn(mdn).orElseThrow(
                () -> new DomainException(ErrorType.VEHICLE_NOT_FOUND)
        );
    }

    @Override
    public Page<Vehicle> getVehicleListWithFilter(
            Long companyId,
            Long departmentId,
            boolean onlyFree,
            Pageable pageable
    ) {
        if (departmentId == null && !onlyFree) {
            return vehicleRepository.findAllByCompanyIdAndDeleteStatus(companyId, pageable, false);
        } else if (departmentId != null && !onlyFree) {
            return vehicleRepository.findAllByCompanyIdAndDepartmentIdAndDeleteStatus(companyId, departmentId, pageable, false);
        } else if (departmentId == null && onlyFree) {
            return vehicleRepository.findFreeByCompanyId(companyId, VehicleStatus.RESERVATION, pageable);
        } else {
            return vehicleRepository.findFreeByCompanyIdAndDepartmentId(companyId, departmentId, VehicleStatus.RESERVATION, pageable);
        }
    }

    @Override
    public Page<Vehicle> getVehicleSearch(Long companyId, String vehicleNumber, Pageable pageable) {

        return vehicleRepository.findByVehicleByCompanyIdAndInformation_VehicleNumber(
                companyId,
                vehicleNumber,
                pageable);
    }


}
