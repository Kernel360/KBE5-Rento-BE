package com.kbe5.api.domain.department.controller;


import com.kbe5.api.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.api.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.api.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.common.response.api.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentController {

    //부서 등록
    ResponseEntity<ApiResponse<String>> registerDepartment(CustomManagerDetails manager, DepartmentRegisterRequest departmentRegisterRequest);
    //부서 목록 조회
    ResponseEntity<ApiResponse<List<DepartmentInfoResponse>>> getAllDepartments(String companyCode);
    //부서 수정
    ResponseEntity<ApiResponse<DepartmentInfoResponse>> updateDepartment (CustomManagerDetails manager, Long departmentId, DepartmentUpdateRequest departmentUpdateRequest);
    //부서 삭제
    ResponseEntity<ApiResponse<String>> deleteDepartment (CustomManagerDetails manager, Long departmentId);

}
