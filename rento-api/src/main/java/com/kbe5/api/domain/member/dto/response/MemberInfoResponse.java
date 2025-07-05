package com.kbe5.api.domain.member.dto.response;


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
}
