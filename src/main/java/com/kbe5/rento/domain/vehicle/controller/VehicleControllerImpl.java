package com.kbe5.rento.domain.vehicle.controller;


import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.rento.domain.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleControllerImpl implements VehicleController{

    private final VehicleService vehicleService;

    @Override
    @PostMapping()
    public ResponseEntity<VehicleResponse> addVehicle(Manager manager, @RequestBody @Validated VehicleAddRequest request) {
        return ResponseEntity.ok(vehicleService.addVehicle(manager, request));
    }

    @Override
    @PutMapping("/{vehicleId}")
    public ResponseEntity<String> updateVehicle(@PathVariable Long vehicleId,
                              @RequestBody @Validated VehicleUpdateRequest request) {
        vehicleService.updateVehicle(vehicleId, request);
        return ResponseEntity.ok("차량 정보 수정 완료");
    }

    @Override
    @DeleteMapping()
    public ResponseEntity<String> deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.ok("차량이 등록 해제되었습니다.");
    }

    // todo: 부서별 필터 기능 적용및 페이징
    @Override
    @GetMapping()
    public ResponseEntity<List<VehicleResponse>> getVehicleList(Manager manager) {
        return ResponseEntity.ok(vehicleService.getVehicleList(manager));
    }

    @Override
    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleDetailResponse> getVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(vehicleService.getVehicle(vehicleId));
    }
}
