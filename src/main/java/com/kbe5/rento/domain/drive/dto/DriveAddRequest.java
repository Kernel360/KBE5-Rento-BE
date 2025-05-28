package com.kbe5.rento.domain.drive.dto;

import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DriveAddRequest(

        @NotNull(message = "멤버는 null 일수 없습니다")
        Member member,

        @NotNull(message = "차량는 null 일수 없습니다")
        Vehicle vehicle,

        @NotNull(message = "운행 타입은 null 일수 없습니다")
        DriveType driveType,

        @NotBlank(message = "출발는 빈 값일수 일수 없습니다")
        String startLocation,

        @NotBlank(message = "멤버는 빈 값일수 없습니다")
        String endLocation
) {
    public static Drive toEntity(DriveAddRequest request) {
        return Drive.builder()
                .member(request.member())
                .vehicle(request.vehicle())
                .dirveType(request.driveType())
                .startLocation(request.startLocation())
                .endLocation(request.endLocation())
                .build();
    }
}
