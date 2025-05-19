package com.kbe5.rento.domain.vehicle.controller;


import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.rento.domain.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleControllerImpl implements VehicleController{

    private final VehicleService vehicleService;

    @Override
    @PostMapping()
    public void addVehicle(@RequestBody VehicleAddRequest request) {
        vehicleService.addVehicle(request);
    }

    @Override
    @PutMapping()
    public void updateVehicle(@PathVariable Long vehicleId,
                              @RequestBody VehicleUpdateRequest request) {
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
    public List<VehicleResponse> getVehicleList(String vehicleCode) {
        return vehicleService.getVehicleList(vehicleCode);
    }

    @Override
    @GetMapping("/detail")
    public VehicleDetailResponse getVehicle(Long vehicleId) {
        return vehicleService.getVehicle(vehicleId);
    }
}
