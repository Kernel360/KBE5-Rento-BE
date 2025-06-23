package com.kbe5.api.domain.member.dto.request;

import com.kbe5.domain.member.entity.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberUpdateRequest(
        @NotBlank(message = "이름은 필수 값입니다.")
        String name,
        @NotBlank(message = "이메일은 필수 값입니다.")
        String email,
        @NotBlank(message = "직책은 필수 값입니다.")
        String position,
        @NotNull(message = "부서는 필수 값입니다.")
        Long departmentId,
        @NotBlank(message = "전화번호는 필수 값입니다.")
        String phoneNumber,
        @NotBlank(message = "아이디는 필수 값입니다.")
        String loginId,
        @NotBlank(message = "기업코드는 필수 값입니다.")
        String companyCode
) {
        public Position getPosition(){
                return Position.fromValue(this.position);
        }
}
