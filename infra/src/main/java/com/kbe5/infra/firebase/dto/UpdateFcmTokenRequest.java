package com.kbe5.infra.firebase.dto;

import jakarta.validation.constraints.*;

public record UpdateFcmTokenRequest(
        @NotBlank(message = "fcm 토큰값이 존재하지 않습니다.")
        String token
) {
}
