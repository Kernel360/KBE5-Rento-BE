package com.kbe5.api.domain.drive.dto;


import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record DriveAddRequest(

        @NotNull(message = "멤버는 null 일수 없습니다")
        Member member,

        @NotNull(message = "차량는 null 일수 없습니다")
        Vehicle vehicle,

        @NotNull(message = "운행 타입은 null 일수 없습니다")
        DriveType driveType,

        @NotBlank(message = "출발는 빈 값일수 없습니다")
        String startLocation,

        @NotNull(message = "운행 시작 일자는 빈값 일수 없습니다")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDateTime,

        @NotNull(message = "운행 시작 일자는 빈값 일수 없습니다")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endDateTime,

        @NotBlank(message = "도착지는 빈 값일수 없습니다")
        String endLocation
) {
    public static Drive toEntity(DriveAddRequest request) {
        return Drive.builder()
                .member(request.member())
                .vehicle(request.vehicle())
                .driveType(request.driveType())
                .startLocation(request.startLocation())
                .endLocation(request.endLocation())
                .startDate(request.startDateTime())
                .endDate(request.endDateTime())
                .build();
    }
}
