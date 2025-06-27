package com.kbe5.api.domain.drive.dto;


import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DriveResponse(
        Long id,
        
        String memberName,

        String vehicleNumber,

        LocalDateTime startDate,

        LocalDateTime endDate,

        String startLocation,

        String endLocation,

        DriveStatus status
) {
    public static DriveResponse fromEntity(Drive drive){
        return DriveResponse.builder()
                .id(drive.getId())
                .memberName(drive.getMember().getName())
                .vehicleNumber(drive.getVehicle().getInfo().getVehicleNumber())
                .startDate(drive.getStartDate())
                .endDate(drive.getEndDate())
                .startLocation(drive.getStartLocation())
                .endLocation(drive.getEndLocation())
                .status(drive.getDriveStatus())
                .build();
    }
}
