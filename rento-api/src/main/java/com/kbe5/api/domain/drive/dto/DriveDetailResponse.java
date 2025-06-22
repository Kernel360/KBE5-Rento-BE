package com.kbe5.api.domain.drive.dto;


import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import com.kbe5.domain.drive.entity.DriveType;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.vehicle.entity.Vehicle;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DriveDetailResponse(
    Member member,

    Vehicle vehicle,

    DriveType driveType,

    LocalDateTime startDate,

    LocalDateTime endDate,

    String startLocation,

    String endLocation,

    Long distance,

    DriveStatus driveStatus
) {
    public static DriveDetailResponse fromEntity(Drive drive){
        return DriveDetailResponse.builder()
                .member(drive.getMember())
                .vehicle(drive.getVehicle())
                .driveType(drive.getDriveType())
                .startDate(drive.getStartDate())
                .endDate(drive.getEndDate())
                .startLocation(drive.getStartLocation())
                .endLocation(drive.getEndLocation())
                .distance(drive.getDistance())
                .driveStatus(drive.getDriveStatus())
                .build();
    }
}