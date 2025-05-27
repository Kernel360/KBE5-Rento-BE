package com.kbe5.rento.domain.vehicle.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    // test
    private final ManagerRepository managerRepository;

    public Vehicle addVehicle(Manager manager, Vehicle vehicle) {
        // 같은 번호는 에러 처리해줍니다
        vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber())
                .ifPresent((__) -> {
                    throw new DomainException(ErrorType.SAME_VIHICLE_NUMBER);
                });

        return vehicleRepository.save(vehicle);
    }

    public List<VehicleResponse> getVehicleList(Manager manager) {
        // test
        Manager manager1 = managerRepository.findById(1L).orElseThrow();

        List<Vehicle> vehicleList = vehicleRepository.findByCompany(manager1.getCompany());

        return vehicleList.stream().map(VehicleResponse::fromEntity).toList();
    }

    public VehicleDetailResponse getVehicle(Long vehicleId){
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new DomainException(ErrorType.VEHICLE_NOT_FOUND) );

        return VehicleDetailResponse.fromEntity(vehicle);
    }

    @Transactional
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