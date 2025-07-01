package com.kbe5.api.domain.member.dto.request;

import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import jakarta.validation.constraints.*;

public record MemberRegisterRequest(
        @NotBlank(message = "이름은 필수 값입니다.")
        @Size(max = 10, message = "이름은 10자 이내여야 합니다.")
        String name,

        @NotBlank(message = "이메일은 필수 값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "직책은 필수 값입니다.")
        String position,

        @NotBlank(message = "아이디는 필수 값입니다.")
        @Size(max = 10, message = "아이디는 10자 이내여야 합니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수 값입니다.")
        @Size(min = 4, message = "비밀번호는 4자리 이상이어야합니다.")
        String password,

        @NotBlank(message = "전화번호는 필수 값입니다.")
        @Pattern(
                regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$",
                message = "올바른 전화번호 형식이 아닙니다."
        )
        String phoneNumber,

        @NotNull(message = "부서는 필수 값입니다.")
        Long departmentId,

        @NotBlank(message = "기업코드는 필수 값입니다.")
        String companyCode
) {
        public static Member toEntity(MemberRegisterRequest request) {
                return Member.builder()
                        .name(request.name())
                        .email(request.email())
                        .position(request.getPosition())
                        .loginId(request.loginId())
                        .password(request.password())
                        .phoneNumber(request.phoneNumber())
                        .companyCode(request.companyCode())
                        .build();
        }

        public Position getPosition(){
                return Position.fromValue(this.position);
        }

}
