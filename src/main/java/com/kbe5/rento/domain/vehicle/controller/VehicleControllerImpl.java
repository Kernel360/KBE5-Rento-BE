package com.kbe5.rento.domain.vehicle.controller;


import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.common.apiresponse.ApiResultCode;
import com.kbe5.rento.common.apiresponse.ResEntityFactory;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleControllerImpl implements VehicleController{

    private final VehicleService vehicleService;

    @Override
    @PostMapping()
    public ResponseEntity<ApiResponse<VehicleResponse>> addVehicle(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @RequestBody @Validated VehicleAddRequest request) {
        Vehicle vehicle = VehicleAddRequest.toEntity(customManagerDetails.getManager(), request);
        VehicleResponse response = VehicleResponse.fromEntity(vehicleService
                .addVehicle(vehicle));

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, response);
    }

    @Override
    @PutMapping("/{vehicleId}")
    public ResponseEntity<ApiResponse<String>> updateVehicle(@PathVariable Long vehicleId,
                              @RequestBody @Validated VehicleUpdateRequest request) {
        vehicleService.updateVehicle(vehicleId, request);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "차량 정보 변경이 완료되었습니다!");
    }

    @Override
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<ApiResponse<String>> deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "차량이 등록 해제되었습니다.");
    }

    // todo: 부서별 필터 기능 적용및 페이징
    @Override
    @GetMapping()
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getVehicleList(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                vehicleService.getVehicleList(customManagerDetails.getManager())
                        .stream()
                        .map(VehicleResponse::fromEntity)
                        .toList());
    }

    @Override
    @GetMapping("/{vehicleId}")
    public ResponseEntity<ApiResponse<VehicleDetailResponse>> getVehicle(@PathVariable Long vehicleId) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                VehicleDetailResponse.fromEntity(vehicleService.getVehicle(vehicleId)));
    }
}
