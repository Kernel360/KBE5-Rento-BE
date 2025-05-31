package com.kbe5.rento.domain.vehicle.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleUpdateRequest(

        @NotNull(message = "총 키로수는 null일수 없습니다")
        Long totalDistanceKm,

        @NotBlank(message = "배터리 전압은 null일 수 없습니다")
        String batteryVoltage
) {
}
