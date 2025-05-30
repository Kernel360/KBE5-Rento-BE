package com.kbe5.rento.domain.department.controller;


import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.common.apiresponse.ApiResultCode;
import com.kbe5.rento.common.apiresponse.ResEntityFactory;
import com.kbe5.rento.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.rento.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.rento.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.service.DepartmentService;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.entity.Manager;
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

        Department createdDepartment = departmentService.register(
                DepartmentRegisterRequest.of(departmentRegisterRequest, manager.getCompany())
        );

        return ResEntityFactory.toResponse(
                ApiResultCode.SUCCESS, createdDepartment.getDepartmentName() + "성공적으로 등록되었습니다."
        );
    }

    //부서 목록 조회
    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentInfoResponse>>> getAllDepartments(@RequestParam String companyCode) {
        List<DepartmentInfoResponse> departments = departmentService.getDepartments(companyCode);

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
        Manager manager = customManagerDetails.getManager();

        DepartmentInfoResponse response = departmentService.updateDepartment(manager, departmentId, departmentUpdateRequest);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, response);
    }

    //부서 삭제
    @Override
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable Long departmentId) {
        Manager manager = customManagerDetails.getManager();

        departmentService.delete(manager, departmentId);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "success");
    }

}
