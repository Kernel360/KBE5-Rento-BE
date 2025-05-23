package com.kbe5.rento.domain.vehicle.controller;


import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.rento.domain.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
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
    public VehicleResponse addVehicle(Manager manager, @RequestBody @Validated VehicleAddRequest request) {
        // test
        return vehicleService.addVehicle(/*manager, */request);
    }

    @Override
    @PutMapping("/{vehicleId}")
    public void updateVehicle(@PathVariable Long vehicleId,
                              @RequestBody @Validated VehicleUpdateRequest request) {
        vehicleService.updateVehicle(vehicleId, request);
    }

    @Override
    @DeleteMapping()
    public void deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
    }

    // todo: 부서별 필터 기능 적용및 페이징
    @Override
    @GetMapping()
    public List<VehicleResponse> getVehicleList(Manager manager) {
        return vehicleService.getVehicleList(manager);
    }

    @Override
    @GetMapping("/{vehicleId}")
    public VehicleDetailResponse getVehicle(@PathVariable Long vehicleId) {
        return vehicleService.getVehicle(vehicleId);
    }
}
