package com.kbe5.rento.domain.drive.dto;

import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
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

    boolean isStart
) {
    public static DriveDetailResponse fromEntity(Drive drive){
        return DriveDetailResponse.builder()
                .member(drive.getMember())
                .vehicle(drive.getVehicle())
                .driveType(drive.getDirveType())
                .startDate(drive.getStartDate())
                .endDate(drive.getEndDate())
                .startLocation(drive.getStartLocation())
                .endLocation(drive.getEndLocation())
                .isStart(drive.isStart())
                .build();
    }
}

