package com.kbe5.domain.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class VehicleUpdateCommand {
    @NotNull(message = "총 키로수는 null일수 없습니다")
    Long totalDistanceKm;
    @NotBlank(message = "배터리 전압은 null일 수 없습니다")
    String batteryVoltage;
}
