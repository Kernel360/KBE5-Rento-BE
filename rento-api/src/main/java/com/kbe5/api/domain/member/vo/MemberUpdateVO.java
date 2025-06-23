package com.kbe5.api.domain.member.vo;

import com.kbe5.api.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import lombok.Getter;

@Getter
public class MemberUpdateVO {

    private final String name;
    private final String email;
    private final Position position;
    private final Long departmentId;
    private final String phoneNumber;
    private final String loginId;
    private final String companyCode;

    public MemberUpdateVO(MemberUpdateRequest request) {
        this.name = request.name();
        this.email = request.email();
        this.position = Position.fromValue(request.position()); // 수정
        this.departmentId = request.departmentId();
        this.phoneNumber = request.phoneNumber();
        this.loginId = request.loginId();
        this.companyCode = request.companyCode();
    }

    public void toUpdate(Member member, Department department) {
        member.update(name, email, position, department, phoneNumber, loginId, companyCode);
    }
}
