package com.kbe5.rento;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.department.entity.Department;

public class DepartmentTestFixtures {
    public static Department departmentA(Company company){
        return Department.builder()
                .company(company)
                .departmentName("개발팀")
                .build();
    }
}
