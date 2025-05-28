package com.kbe5.rento.domain.vehicle.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle addVehicle(Vehicle vehicle) {
        vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber())
                .ifPresent((__) -> {
                    throw new DomainException(ErrorType.SAME_VIHICLE_NUMBER);
                });

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getVehicleList(Manager manager) {
        return vehicleRepository.findByCompany(manager.getCompany());
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