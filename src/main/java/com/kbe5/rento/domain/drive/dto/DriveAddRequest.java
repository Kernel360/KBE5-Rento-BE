package com.kbe5.rento.domain.drive.dto;

import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DriveAddRequest(
        @NotNull
        Member member,
        @NotNull
        Vehicle vehicle,
        @NotNull
        DriveType driveType,
        @NotBlank
        String startLocation,
        @NotBlank
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
