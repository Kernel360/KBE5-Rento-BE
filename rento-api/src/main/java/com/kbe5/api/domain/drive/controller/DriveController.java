package com.kbe5.api.domain.drive.controller;


import com.kbe5.api.domain.drive.dto.DriveAddRequest;
import com.kbe5.api.domain.drive.dto.DriveDetailResponse;
import com.kbe5.api.domain.drive.dto.DriveResponse;
import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.common.response.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "운행 서비스 API", description = "운행 등록, 취소, 목록 조회, 상세 조회 기능을 제공합니다. 매니저 로그인 후 사용 가능합니다.")
public interface DriveController {

    @Operation(summary = "운행 등록", description = "새로운 운행을 등록합니다.")
    @RequestBody(description = "운행 등록 정보 요청",  required = true)
    ResponseEntity<ApiResponse<String>> driveAdd(DriveAddRequest request);

    @Operation(summary = "운행 취소", description = "운행을 취소합니다.")
    @Parameter(name = "driveId", description = "운행 취소할 ID", example = "1", required = true)
    ResponseEntity<ApiResponse<String>> driveCancel(Long driveId);

    @Operation(summary = "운행 목록 조회", description = "운행 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<DriveResponse>>> getDriveList(CustomManagerDetails manager);

    @Operation(summary = "운행 상세 조회", description = "운행 상세를 조회합니다.")
    @Parameter(name = "driveId", description = "운행 상세 조회할 ID", example = "1", required = true)
    ResponseEntity<ApiResponse<DriveDetailResponse>> getDriveDetail(Long driveId);

    @Operation(summary = "운행중인 차량 목록 조회", description = "운행 중인 차량 목록을 조회합니다.")
    @Parameter(name = "vehicleNumber", description = "필요시 검색할 차량 번호", example = "123가1234")
    ResponseEntity<ApiResponse<List<DriveResponse>>> getDriving(CustomManagerDetails manager, String vehicleNumber);
}
