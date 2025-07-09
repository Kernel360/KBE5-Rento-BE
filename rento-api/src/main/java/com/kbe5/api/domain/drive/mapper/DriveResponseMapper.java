package com.kbe5.api.domain.drive.mapper;

import com.kbe5.api.domain.drive.dto.DriveDetailResponse;
import com.kbe5.api.domain.drive.dto.DriveResponse;
import com.kbe5.domain.drive.dto.DriveInfo;
import org.springframework.stereotype.Component;

@Component
public class DriveResponseMapper {

    public DriveResponse toDriveResponse(DriveInfo info) {
        return DriveResponse.builder()
                .id(info.getId())
                .mdn(info.getMdn())
                .memberName(info.getMember().getName())
                .vehicleNumber(info.getVehicle().getInformation().getVehicleNumber())
                .startDate(info.getStartDate())
                .endDate(info.getEndDate())
                .startLocation(info.getStartLocation())
                .endLocation(info.getEndLocation())
                .status(info.getDriveStatus())
                .build();
    }

    public DriveDetailResponse toDriveDetailResponse(DriveInfo info) {
        return DriveDetailResponse.builder()
                .memberName(info.getMember().getName())
                .vehicleNumber(info.getVehicle().getInformation().getVehicleNumber())
                .driveType(info.getDriveType())
                .startDate(info.getStartDate())
                .endDate(info.getEndDate())
                .startLocation(info.getStartLocation())
                .endLocation(info.getEndLocation())
                .distance(info.getDistance())
                .driveStatus(info.getDriveStatus())
                .build();
    }
}
