package com.kbe5.rento.domain.department.controller;

import com.kbe5.rento.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.rento.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.rento.domain.department.dto.response.DepartmentInfoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentController {

    //부서 등록
    ResponseEntity<String> registerDepartment(DepartmentRegisterRequest departmentRegisterRequest);
    //부서 목록 조회
    ResponseEntity<List<DepartmentInfoResponse>> getAllDepartments(String companyCode);
    //부서 수정
    ResponseEntity<DepartmentInfoResponse> updateDepartment (Long departmentId, DepartmentUpdateRequest departmentUpdateRequest);
    //부서 삭제
    ResponseEntity<String> deleteDepartment (Long departmentId);

}
