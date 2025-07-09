package com.kbe5.domain.drive.dto;

import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveType;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.vehicle.entity.Vehicle;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DriveAddCommand {
    Member member;
    Vehicle vehicle;
    DriveType driveType;
    String startLocation;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    String endLocation;

    public Drive toEntity() {
        return Drive.builder()
                .member(member)
                .vehicle(vehicle)
                .driveType(driveType)
                .startLocation(startLocation)
                .startDate(startDateTime)
                .endLocation(endLocation)
                .endDate(endDateTime)
                .build();
    }

}
