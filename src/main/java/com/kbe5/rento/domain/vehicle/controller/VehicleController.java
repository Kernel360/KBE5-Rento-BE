package com.kbe5.rento.domain.vehicle.controller;

import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleController {

    ResponseEntity<ApiResponse<String>> addVehicle(CustomManagerDetails manager,
                                                            VehicleAddRequest request);
    ResponseEntity<ApiResponse<String>> updateVehicle(Long vehicleId, VehicleUpdateRequest request);
    ResponseEntity<ApiResponse<String>> deleteVehicle(Long vehicleId);
    ResponseEntity<ApiResponse<PagedModel<VehicleResponse>>> getVehicleList(CustomManagerDetails manager,
                                                                            Long departmentId,
                                                                            boolean onlyfree,
                                                                            Pageable pageable);
    ResponseEntity<ApiResponse<VehicleDetailResponse>> getVehicle(Long vehicleId);
}
