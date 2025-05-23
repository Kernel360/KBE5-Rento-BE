package com.kbe5.rento.domain.vehicle.service;

import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.entity.ManagerRepository;
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

    public VehicleResponse addVehicle(VehicleAddRequest request) {

        Manager manager = managerRepository.findById(1L).orElseThrow();
        // 테스트용 manager 생성
        Vehicle vehicle = VehicleAddRequest.toEntity(manager, request);

        vehicleRepository.save(vehicle);

        return VehicleResponse.fromEntity(vehicle);
    }

    public List<VehicleResponse> getVehicleList(Manager manager) {
        // test
        Manager manager1 = managerRepository.findById(1L).orElseThrow();

        List<Vehicle> vehicleList = vehicleRepository.findByCompanyCode(manager1.getComponyCode());

        return vehicleList.stream().map(VehicleResponse::fromEntity).toList();
    }

    public VehicleDetailResponse getVehicle(Long vehicleId){
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new IllegalArgumentException("해당 차량 없음"));

        return VehicleDetailResponse.fromEntity(vehicle);
    }

    @Transactional
    public void updateVehicle(Long vehicleId, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new IllegalArgumentException("해당 차량 없음"));

        vehicle.update(request);
    }

    public void deleteVehicle(Long vehicleId){
        vehicleRepository.deleteById(vehicleId);
    }

    public VehicleResponse getVehicleSearch(String vehicleNumber){
        Vehicle vehicle = vehicleRepository.findByVehicleNumber(vehicleNumber).orElseThrow(
                () -> new IllegalArgumentException("존재하는 차량이 없습니다")
        );

        return VehicleResponse.fromEntity(vehicle);
    }
}