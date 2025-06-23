package com.kbe5.api.domain.stream.dto;

import com.kbe5.domain.event.entity.CycleInfo;

import java.math.BigDecimal;

public record StreamInfoRequest(
        Long driveId,
        //LocalDateTime timestamp,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer speed,
        Integer angle
) {
    // MQ에 들어가있는 초당 정보를 저장전에 consumer에서 빼와야할듯?
     public static StreamInfoRequest of(CycleInfo info) {
         return new StreamInfoRequest(
                 info.getDriveId(),
                 info.getLatitude(),
                 info.getLongitude(),
                 info.getSpeed(),
                 info.getAngle()
         );
     }

}

