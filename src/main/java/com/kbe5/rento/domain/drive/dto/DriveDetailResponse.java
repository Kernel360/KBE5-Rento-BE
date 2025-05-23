package com.kbe5.rento.domain.drive.dto;

import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.entity.DriveType;
import com.kbe5.rento.domain.member.entity.Member;

import java.time.LocalDateTime;

public record DriveDetailResponse(
    Member member,
    DriveType driveType,
    LocalDateTime startDate,
    LocalDateTime endDate,
    String startLocation,
    String endLocation
) {
    public static DriveDetailResponse fromEntity(Drive drive){
        return new DriveDetailResponse(drive.getMember(), drive.getDirveType(), drive.getStartDate(),
                drive.getEndDate(), drive.getStartLocation(), drive.getEndLocation());
    }
}
