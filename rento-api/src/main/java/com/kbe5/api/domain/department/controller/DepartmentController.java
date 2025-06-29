package com.kbe5.api.domain.department.controller;


import com.kbe5.api.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.api.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.api.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.common.response.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "부서 서비스 API", description = "부서 등록, 수정, 삭제, 목록 조회 기능을 제공합니다. 매니저 로그인 후 사용 가능합니다.")
public interface DepartmentController {

    //부서 등록
    @Operation(summary = "부서 등록", description = "새로운 부서를 등록합니다")
    @RequestBody(
            description = "부서 등록 요청 정보",
            required = true
    )
    ResponseEntity<ApiResponse<String>> registerDepartment(
            CustomManagerDetails manager, DepartmentRegisterRequest departmentRegisterRequest
    );
    //부서 목록 조회
    @Operation(summary = "부서 목록 조회", description = "존재하는 부서 목록을 조회합니다")
    ResponseEntity<ApiResponse<List<DepartmentInfoResponse>>> getAllDepartments(CustomManagerDetails manager);
    //부서 수정
    @Operation(summary = "부서 수정", description = "기존 부서를 수정합니다")
    @Parameter(name = "departmentID", description = "부서ID", example = "1")
    @RequestBody(
            description = "부서 수정 요청 정보",
            required = true
    )
    ResponseEntity<ApiResponse<DepartmentInfoResponse>> updateDepartment (
            CustomManagerDetails manager, Long departmentId, DepartmentUpdateRequest departmentUpdateRequest
    );
    //부서 삭제
    @Operation(summary = "부서 삭제", description = "기존 부서를 삭제합니다. 부서에 존재하는 사용자가 없어야 삭제가능합니다.")
    @Parameter(name = "departmentId",description = "부서ID", example = "1")
    ResponseEntity<ApiResponse<String>> deleteDepartment (CustomManagerDetails manager, Long departmentId);

}
