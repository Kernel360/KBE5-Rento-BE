package com.kbe5.domain.member.dto;


import com.kbe5.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberInfo {
    private final Long id;
    private final String name;
    private final String email;
    private final String position;
    private final String loginId;
    private final String phoneNumber;
    private final Long departmentId;
    private final String departmentName;

    public static MemberInfo fromEntity(Member member) {
        return MemberInfo.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .position(member.getPosition())
                .loginId(member.getLoginId())
                .phoneNumber(member.getPhoneNumber())
                .departmentId(member.getDepartment().getId())
                .departmentName(member.getDepartment().getDepartmentName())
                .build();
    }
}
