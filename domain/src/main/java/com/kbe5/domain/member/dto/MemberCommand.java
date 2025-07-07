package com.kbe5.domain.member.dto;

import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

public class MemberCommand {
    @Getter
    @Builder
    public static class Register{
        private final String name;
        private final String email;

        @Enumerated(EnumType.STRING)
        private final Position position;

        private final String loginId;
        private final String password;
        private final String phoneNumber;
        private final String companyCode;

        private final Long departmentId;

        public Member toEntity(){
            return Member.builder()
                    .name(name)
                    .email(email)
                    .position(position)
                    .loginId(loginId)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .companyCode(companyCode)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Update{
        private final String name;
        private final String email;
        private final Position position;
        private final String loginId;
        private final String phoneNumber;
        private final String companyCode;
        private final Long departmentId;
    }
}
