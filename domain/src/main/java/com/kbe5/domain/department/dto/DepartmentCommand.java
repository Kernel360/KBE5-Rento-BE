package com.kbe5.domain.department.dto;

import com.kbe5.domain.department.entity.Department;
import lombok.Builder;
import lombok.Getter;

public class DepartmentCommand {

    @Getter
    @Builder
    public static class Register {

        private final String departmentName;
        private final Long companyId;

        public Department toEntity() {
            return Department.builder()
                    .departmentName(departmentName)
                    .companyId(companyId)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Update {
        private final String companyCode;
        private final String departmentName;
    }
}
