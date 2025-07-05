package com.kbe5.api.domain.department.mapper;

import com.kbe5.api.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.domain.department.dto.DepartmentInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentResponseMapper {

    public DepartmentInfoResponse toResponse(DepartmentInfo departmentInfo) {
        return new DepartmentInfoResponse(
                departmentInfo.getDepartmentId(),
                departmentInfo.getDepartmentName(),
                departmentInfo.getNumberOfEmployees()
        );
    }

    public List<DepartmentInfoResponse> toResponseList(List<DepartmentInfo> departmentInfos) {
        return departmentInfos.stream()
                .map(this::toResponse)
                .toList();
    }
}