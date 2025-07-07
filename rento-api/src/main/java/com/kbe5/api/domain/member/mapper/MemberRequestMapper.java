package com.kbe5.api.domain.member.mapper;

import com.kbe5.api.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.api.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.domain.member.dto.MemberCommand;
import org.springframework.stereotype.Component;

@Component
public class MemberRequestMapper {
    public MemberCommand.Register toRegisterCommand(MemberRegisterRequest request) {
        return MemberCommand.Register.builder()
                .name(request.name())
                .email(request.email())
                .position(request.getPosition())
                .loginId(request.loginId())
                .password(request.password())
                .phoneNumber(request.phoneNumber())
                .companyCode(request.companyCode())
                .departmentId(request.departmentId())
                .build();
    }

    public MemberCommand.Update toUpdateCommand(MemberUpdateRequest request) {
        return MemberCommand.Update.builder()
                .name(request.name())
                .email(request.email())
                .position(request.getPosition())
                .loginId(request.loginId())
                .phoneNumber(request.phoneNumber())
                .companyCode(request.companyCode())
                .departmentId(request.departmentId())
                .build();
    }
}
