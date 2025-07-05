package com.kbe5.api.domain.department.controller;


import com.kbe5.api.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.api.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.api.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.department.dto.DepartmentInfo;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.service.DepartmentService;
import com.kbe5.domain.manager.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentControllerImpl implements DepartmentController {

    private final DepartmentService departmentService;

    //부서 등록
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<String>> registerDepartment(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @RequestBody @Validated DepartmentRegisterRequest departmentRegisterRequest
    ) {
        Manager manager = customManagerDetails.getManager();

        DepartmentInfo departmentInfo = departmentService.registerDepartment(
                departmentRegisterRequest.toCommand(manager.getCompany().getId())
        );

        return ResEntityFactory.toResponse(
                ApiResultCode.SUCCESS, departmentInfo.getDepartmentName() + "성공적으로 등록되었습니다."
        );
    }

    //부서 목록 조회
    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentInfoResponse>>> getAllDepartments(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails
    ) {
        List<DepartmentInfo> departmentInfos = departmentService.getDepartments(
                customManagerDetails.getManager().getCompany().getId());

        List<DepartmentInfoResponse> departments = departmentInfos.stream()
                .map(DepartmentInfoResponse::fromDepartmentInfo)
                .toList();

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, departments);
    }

    //부서 수정
    @Override
    @PutMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<DepartmentInfoResponse>> updateDepartment(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable Long departmentId,
            @Validated @RequestBody DepartmentUpdateRequest departmentUpdateRequest
    ) {
        DepartmentInfo departmentInfo = departmentService.updateDepartment(
                departmentId,
                departmentUpdateRequest.toCommand()
        );

        DepartmentInfoResponse response = DepartmentInfoResponse.fromDepartmentInfo(departmentInfo);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, response);
    }

    //부서 삭제
    @Override
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable Long departmentId) {

        departmentService.delete(departmentId);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "success");
    }

}
