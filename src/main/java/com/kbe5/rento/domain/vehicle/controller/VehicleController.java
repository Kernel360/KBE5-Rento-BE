package com.kbe5.rento.domain.vehicle.controller;

import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleController {

    ResponseEntity<VehicleResponse> addVehicle(CustomManagerDetails manager, VehicleAddRequest request);
    ResponseEntity<String> updateVehicle(Long vehicleId, VehicleUpdateRequest request);
    ResponseEntity<String> deleteVehicle(Long vehicleId);
    ResponseEntity<List<VehicleResponse>> getVehicleList(CustomManagerDetails manager);
    ResponseEntity<VehicleDetailResponse> getVehicle(Long vehicleId);
}
