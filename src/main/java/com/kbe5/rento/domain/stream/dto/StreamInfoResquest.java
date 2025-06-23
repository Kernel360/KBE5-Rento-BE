package com.kbe5.rento.domain.stream.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StreamInfoResquest(
        Long driveId,
        LocalDateTime timestamp,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer speed,
        Integer angle
) {


}
