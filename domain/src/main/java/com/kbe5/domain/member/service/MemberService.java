package com.kbe5.domain.member.service;

import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.member.dto.MemberCommand;
import com.kbe5.domain.member.dto.MemberInfo;
import com.kbe5.domain.member.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberInfo registerMember(MemberCommand.Register command);
    Page<MemberInfo> getMembers(Long companyId,
                                Position position,
                                Long departmentId,
                                String search,
                                Pageable pageable);
    MemberInfo getMember(Long memberId);
    MemberInfo updateMember(Long memberId, MemberCommand.Update memberCommand);
    void delete(Long memberId);
    boolean isExistLoginId(String companyCode, String loginId);
    boolean isExistEmail(String companyCode, String email);
    boolean isExistPhoneNumber(String companyCode, String phoneNumber);
}
