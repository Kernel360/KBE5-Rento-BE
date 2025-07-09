package com.kbe5.api.domain.drive.mapper;

import com.kbe5.api.domain.drive.dto.DriveAddRequest;
import com.kbe5.domain.drive.dto.DriveAddCommand;
import org.springframework.stereotype.Component;

@Component
public class DriveRequestMapper {

    public DriveAddCommand toAddCommand(DriveAddRequest request) {
        return DriveAddCommand.builder()
                .member(request.member())
                .vehicle(request.vehicle())
                .driveType(request.driveType())
                .startLocation(request.startLocation())
                .endLocation(request.endLocation())
                .startDateTime(request.startDateTime())
                .endDateTime(request.endDateTime())
                .build();
    }
}
