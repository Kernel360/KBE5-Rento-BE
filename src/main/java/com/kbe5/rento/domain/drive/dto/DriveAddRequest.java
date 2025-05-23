package com.kbe5.rento.domain.drive.dto;

import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;

public record DriveAddRequest(
        @NotBlank
        Member member,
        @NotBlank
        Vehicle vehicle,
        @NotBlank
        DriveType driveType,
        @NotBlank
        String startLocation,
        @NotBlank
        String endLocation,
        @NotBlank
        String companyCode
) {
    public static Drive toEntity(DriveAddRequest request) {
        return Drive.builder()
                .member(request.member())
                .vehicle(request.vehicle())
                .dirveType(request.driveType())
                .startLocation(request.startLocation())
                .endLocation(request.endLocation())
                .companyCode(request.companyCode())
                .build();
    }


}
