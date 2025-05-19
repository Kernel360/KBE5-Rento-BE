package com.kbe5.rento.domain.vehicle.service;

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

    // 차량 등록
    public void addVehicle(VehicleAddRequest request) {

        Vehicle vehicle = VehicleAddRequest.toEntity(request);
        vehicleRepository.save(vehicle);
    }

    // 차량 목록 조회
    // todo: 업체, 부서 쿼리 작성 해야할듯(페이징)
    public List<VehicleResponse> getVehicleList(String companyCode){

        List<Vehicle> vehicleList = vehicleRepository.findByCompanyCode(companyCode);

        List<VehicleResponse> responses = vehicleList.stream()
                .map(VehicleResponse::fromEntity).toList();

        return responses;
    }

    // 차량 상세 조회
    public VehicleDetailResponse getVehicle(Long vehicleId){
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new IllegalArgumentException("해당 차량 없음"));

        return VehicleDetailResponse.fromEntity(vehicle);
    }


    // 차량 정보 수정
    @Transactional
    public void updateVehicle(Long vehicleId, VehicleUpdateRequest request) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new IllegalArgumentException("해당 차량 없음"));
        vehicle.update(request);
    }


    // 차량 등록 해제
    public void deleteVehicle(Long vehicleId){
        vehicleRepository.deleteById(vehicleId);
    }
}
