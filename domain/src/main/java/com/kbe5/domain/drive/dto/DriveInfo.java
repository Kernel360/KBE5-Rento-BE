package com.kbe5.domain.drive.dto;

import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import com.kbe5.domain.drive.entity.DriveType;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.vehicle.entity.Vehicle;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DriveInfo {
    private Long id;
    private Member member;
    private Vehicle vehicle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DriveType driveType;
    private String startLocation;
    private String endLocation;
    private Long distance;
    private DriveStatus driveStatus;
    private Long mdn;

    public static DriveInfo fromEntity(Drive drive) {
        return DriveInfo.builder()
                .id(drive.getId())
                .member(drive.getMember())
                .vehicle(drive.getVehicle())
                .startDate(drive.getStartDate())
                .endDate(drive.getEndDate())
                .driveType(drive.getDriveType())
                .startLocation(drive.getStartLocation())
                .endLocation(drive.getEndLocation())
                .distance(drive.getDistance())
                .driveStatus(drive.getDriveStatus())
                .mdn(drive.getMdn())
                .build();
    }
}
