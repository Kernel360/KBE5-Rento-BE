package com.kbe5.rento.domain.member.dto.response;

import com.kbe5.rento.domain.member.entity.Member;

public record MemberInfoResponse(
        Long id,
        String name,
        String email,
        String position,
        String login_id,
        String phoneNumber,
        Long departmentId,
        String departmentName
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPosition(),
                member.getLoginId(),
                member.getPhoneNumber(),
                member.getDepartment().getId(),
                member.getDepartment().getDepartmentName()
        );
    }

}
