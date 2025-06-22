package com.kbe5.rento.domain.cycleInfoSummary.dto;

import com.kbe5.rento.domain.cycleInfoSummary.entity.CycleInfoSummary;

import java.math.BigDecimal;

public record CycleInfoSummaryResponse(
         BigDecimal latitude,
         BigDecimal longitude
) {
    public static CycleInfoSummaryResponse of(CycleInfoSummary cycleInfoSummary) {
        return new CycleInfoSummaryResponse(cycleInfoSummary.getLatitude(),
                cycleInfoSummary.getLongitude());
    }
}
