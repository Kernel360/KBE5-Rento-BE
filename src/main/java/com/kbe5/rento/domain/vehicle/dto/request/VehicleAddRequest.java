package com.kbe5.rento.domain.vehicle.dto.request;


import com.kbe5.rento.common.util.annotaion.VehicleNumber;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.vehicle.entity.FuelType;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import com.kbe5.rento.domain.vehicle.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record VehicleAddRequest(
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
    public static Vehicle toEntity(Manager manager, VehicleAddRequest request) {
        return Vehicle.builder()
                .company(manager.getCompany())
                .vehicleNumber(request.vehicleNumber)
                .brand(request.brand)
                .modelName(request.modelName)
                .vehicleType(request.vehicleType)
                .fuelType(request.fuelType)
                .totalDistanceKm(request.totalDistanceKm)
                .batteryVoltage(request.batteryVoltage)
                .build();
    }
}
