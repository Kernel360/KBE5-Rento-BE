package com.kbe5.api.domain.vehicle.controller;


import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.api.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.api.domain.vehicle.dto.request.VehicleUpdateRequest;
import com.kbe5.api.domain.vehicle.dto.response.VehicleDetailResponse;
import com.kbe5.api.domain.vehicle.dto.response.VehicleResponse;
import com.kbe5.common.response.api.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "차랑 서비스 API", description = "차량 등록, 수정, 삭제, 목록 조회, 상세 조회 기능, 및 검색을 제공합니다.")
public interface VehicleController {

    @Operation(summary = "차량 등록", description = "새로운 차량을 등록합니다.")
    @RequestBody(description = "차량 등록 요청 정보", required = true)
    ResponseEntity<ApiResponse<String>> addVehicle(CustomManagerDetails manager,
                                                   VehicleAddRequest request);

    @Operation(summary = "차량 정보 변경", description = "차량을 수정합니다.")
    @Parameter(name = "vehicleId", description = "수정할 차량 Id", example = "1", required = true)
    @RequestBody(description = "차량 수정 요청 정보", required = true)
    ResponseEntity<ApiResponse<String>> updateVehicle(Long vehicleId, VehicleUpdateRequest request);

    @Operation(summary = "차량 삭제", description = "차량을 삭제합니다.")
    @Parameter(name = "vehicleId", description = "수정할 차량 Id", example = "1", required = true)
    ResponseEntity<ApiResponse<String>> deleteVehicle(Long vehicleId);

    @Operation(summary = "업체 차량 리스트", description = "차량 목록을 조회합니다.")
    ResponseEntity<ApiResponse<PagedModel<VehicleResponse>>> getVehicleList(
            CustomManagerDetails manager,
            @Parameter(name = "departmentId", description = "부서별 필터링", example = "1", required = true)
            Long departmentId,
            @Parameter(name = "onlyfree", description = "사용 가능한 차량")
            boolean onlyfree,
            @Parameter(hidden = true)
            Pageable pageable);

    @Operation(summary = "차량 상세", description = "차량 상세 정보를 조회합니다.")
    @Parameter(name = "vehicleId", description = "상세 조회할 차량 ID", example = "1", required = true)
    ResponseEntity<ApiResponse<VehicleDetailResponse>> getVehicle(Long vehicleId);

    @Operation(summary = "차량 검색", description = "차량 검색합니다.")
    ResponseEntity<ApiResponse<PagedModel<VehicleResponse>>> search(
            CustomManagerDetails customManagerDetails,
            @Parameter(name = "vehicleNumber", description = "검색할 차량 번호", example = "123가1234")
            String vehicleNumber,
            @Parameter(hidden = true)
            Pageable pageable);
}
