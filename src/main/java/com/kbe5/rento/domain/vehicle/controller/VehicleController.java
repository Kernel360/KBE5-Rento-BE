package com.kbe5.rento.domain.vehicle.controller;

import com.kbe5.rento.common.response.api.ApiResponse;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.rento.domain.vehicle.dto.response.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "차랑 서비스 API")
public interface VehicleController {

    @Operation(summary = "차량 등록")
    ResponseEntity<ApiResponse<String>> addVehicle(CustomManagerDetails manager,
                                                   VehicleAddRequest request);
    @Operation(summary = "차량 정보 변경")
    ResponseEntity<ApiResponse<String>> updateVehicle(Long vehicleId, VehicleUpdateRequest request);
    @Operation(summary = "차량 삭제")
    ResponseEntity<ApiResponse<String>> deleteVehicle(Long vehicleId);
    @Operation(summary = "업체 차량 리스트")
    ResponseEntity<ApiResponse<PagedModel<VehicleResponse>>> getVehicleList(CustomManagerDetails manager,
                                                                            Long departmentId,
                                                                            boolean onlyfree,
                                                                            Pageable pageable);
    @Operation(summary = "차량 상세")
    ResponseEntity<ApiResponse<VehicleDetailResponse>> getVehicle(Long vehicleId);
}
