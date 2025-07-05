package com.kbe5.api.domain.member.mapper;

import com.kbe5.api.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.domain.member.dto.MemberInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MemberResponseMapper {
    public MemberInfoResponse toResponse(MemberInfo info) {
        return new MemberInfoResponse(
                info.getId(),
                info.getName(),
                info.getEmail(),
                info.getPosition(),
                info.getLoginId(),
                info.getPhoneNumber(),
                info.getDepartmentId(),
                info.getDepartmentName()
        );
    }

    public Page<MemberInfoResponse> toResponseList(Page<MemberInfo> memberInfos) {
        return memberInfos.map(this::toResponse);
    }
}
