package com.kbe5.rento;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.member.entity.Member;

import static com.kbe5.rento.DepartmentTestFixtures.departmentA;

public class MemberTestFixtures {
    public static Member memberA(Company company){
        Department department = departmentA(company);

        Member member = Member.builder()
                .name("testMember")
                .companyCode("C1")
                .build();

        member.assignDepartment(department);

        return member;
    }
}
