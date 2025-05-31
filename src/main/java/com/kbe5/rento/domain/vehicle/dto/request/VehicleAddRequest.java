package com.kbe5.rento.domain.vehicle.dto.request;

import com.kbe5.rento.common.util.annotaion.VehicleNumber;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.vehicle.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleAddRequest(
        @NotNull(message = "부서를 선택해 주세요")
        Long departmentId,
        @NotBlank(message = "차량 번호는 null일수 없습니다")
        @VehicleNumber
        String vehicleNumber,
        @NotBlank(message = "차량 제조사는 null일수 없습니다")
        String brand,
        @NotBlank(message = "차량 모델명은 null일수 없습니다")
        String modelName,
        @NotNull(message = "엔진 타입는 null일수 없습니다")
        VehicleType vehicleType,
        @NotNull(message = "연료 타입는 null일수 없습니다")
        FuelType fuelType,
        @NotNull(message = "총 키로수는 null일수 없습니다")
        Long totalDistanceKm,
        @NotBlank(message = "배터리 전압는 null일수 없습니다")
        String batteryVoltage
) {
    public Vehicle toEntity(Company company) {
        VehicleInfo info = new VehicleInfo(vehicleNumber, brand, modelName, vehicleType, fuelType);
        VehicleMilleage milleage = new VehicleMilleage(totalDistanceKm, batteryVoltage);
        return Vehicle.of(company, info, milleage);
    }
}
