package com.kbe5.rento.domain.department.controller;


import com.kbe5.rento.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.rento.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.rento.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.rento.domain.department.service.DepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentControllerImpl implements DepartmentController {

    private final DepartmentService departmentService;

    //부서 등록
    @Override
    @PostMapping
    public ResponseEntity<String> registerDepartment(@RequestBody @Validated DepartmentRegisterRequest departmentRegisterRequest) {
        String response = departmentService.register(departmentRegisterRequest);

        return ResponseEntity.ok(response);
    }

    //부서 목록 조회
    @Override
    @GetMapping
    public ResponseEntity<List<DepartmentInfoResponse>> getAllDepartments(@RequestParam String companyCode) {
        List<DepartmentInfoResponse> departments = departmentService.getDepartments(companyCode);

        return ResponseEntity.ok(departments);
    }

    //부서 수정
    @Override
    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentInfoResponse> updateDepartment(@PathVariable Long departmentId, @Validated @RequestBody DepartmentUpdateRequest departmentUpdateRequest) {
        DepartmentInfoResponse response = departmentService.updateDepartment(departmentId, departmentUpdateRequest);

        return ResponseEntity.ok(response);
    }

    //부서 삭제
    @Override
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long departmentId) {
        String response = departmentService.delete(departmentId);

        return ResponseEntity.ok(response);
    }

}
