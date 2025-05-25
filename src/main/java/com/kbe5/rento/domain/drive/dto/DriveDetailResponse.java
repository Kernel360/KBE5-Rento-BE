package com.kbe5.rento.domain.drive.dto;

import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;

import java.time.LocalDateTime;

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
        return new DriveDetailResponse(drive.getMember(), drive.getVehicle(),drive.getDirveType(),
                drive.getStartDate(), drive.getEndDate(), drive.getStartLocation(),
                drive.getEndLocation(), drive.isStart());
    }
}

