package com.kbe5.api.domain.department.controller;


import com.kbe5.api.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.api.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.api.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.api.domain.department.mapper.DepartmentRequestMapper;
import com.kbe5.api.domain.department.mapper.DepartmentResponseMapper;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.department.dto.DepartmentInfo;
import com.kbe5.domain.department.service.DepartmentService;
import com.kbe5.domain.manager.dto.ManagerInfo;
import com.kbe5.domain.manager.service.ManagerService;
import com.kbe5.infra.security.details.CustomManagerDetails;
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
    private final DepartmentRequestMapper requestMapper;
    private final DepartmentResponseMapper responseMapper;
    private final ManagerService managerService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<String>> registerDepartment(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @RequestBody @Validated DepartmentRegisterRequest departmentRegisterRequest
    ) {
        Long managerId = customManagerDetails.getManager().getId();
        ManagerInfo info = managerService.getManagerInfo(managerId);

        DepartmentInfo departmentInfo = departmentService.registerDepartment(
                requestMapper.toRegisterCommand(departmentRegisterRequest, info.getCompanyId())
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
        Long managerId = customManagerDetails.getManager().getId();
        ManagerInfo info = managerService.getManagerInfo(managerId);

        List<DepartmentInfo> departmentInfos = departmentService.getDepartments(
                info.getCompanyId());

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, responseMapper.toResponseList(departmentInfos));
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
                requestMapper.toUpdateCommand(departmentUpdateRequest)
        );

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, responseMapper.toResponse(departmentInfo));
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
