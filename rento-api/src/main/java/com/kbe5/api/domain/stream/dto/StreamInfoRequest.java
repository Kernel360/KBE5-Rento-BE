package com.kbe5.api.domain.stream.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StreamInfoRequest(
        Long driveId,
        LocalDateTime timestamp,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer speed,
        Integer angle
) {


}

