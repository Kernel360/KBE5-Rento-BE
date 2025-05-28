package com.kbe5.rento.domain.vehicle.dto.request;

import com.kbe5.rento.common.util.annotaion.VehicleNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleUpdateRequest(

        @NotBlank(message = "차량 번호는 null일 수 없습니다")
        @VehicleNumber
        String vehicleNumber,

        @NotNull(message = "총 키로수는 null일수 없습니다")
        Long totalDistanceKm,

        @NotBlank(message = "배터리 전압은 null일 수 없습니다")
        String batteryVoltage
) {
}
