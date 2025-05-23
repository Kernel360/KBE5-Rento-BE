package com.kbe5.rento.domain.drive.dto;

import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;

import java.time.LocalDateTime;

public record DriveResponse(
        Member member,
        Vehicle vehicle,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String startLocation,
        String endLocation
) {
    public static DriveResponse fromEntity(Drive drive){
        return new DriveResponse(drive.getMember(), drive.getVehicle(), drive.getStartDate(),
                drive.getEndDate(), drive.getStartLocation(), drive.getEndLocation());
    }
}
