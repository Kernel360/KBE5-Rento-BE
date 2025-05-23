package com.kbe5.rento.domain.vehicle.controller;

import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;

import java.util.List;

public interface VehicleController {

    VehicleResponse addVehicle(Manager manager, VehicleAddRequest request);
    void updateVehicle(Long vehicleId, VehicleUpdateRequest request);
    void deleteVehicle(Long vehicleId);
    List<VehicleResponse> getVehicleList(Manager manager);
    VehicleDetailResponse getVehicle(Long vehicleId);
}
