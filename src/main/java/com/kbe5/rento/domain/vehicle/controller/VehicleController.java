package com.kbe5.rento.domain.vehicle.controller;

import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;

import java.util.List;

public interface VehicleController {

    void addVehicle(VehicleAddRequest request);
    void updateVehicle(Long vehicleId, VehicleUpdateRequest request);
    void deleteVehicle(Long vehicleId);
    List<VehicleResponse> getVehicleList(String vehicleCode);
    VehicleDetailResponse getVehicle(Long vehicleId);
}
