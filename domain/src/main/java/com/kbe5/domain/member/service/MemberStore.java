package com.kbe5.domain.member.service;

import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.member.entity.Member;

public interface MemberStore {

    Member store(Member member, Department department);

    void delete(Member member);
}
