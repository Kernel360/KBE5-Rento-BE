package com.kbe5.api.domain.cycleinfosummary.dto;


import com.kbe5.domain.cycleinfosummary.dto.CycleInfoSummaryInfo;
import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;

import java.math.BigDecimal;

public record CycleInfoSummaryResponse(
         BigDecimal latitude,
         BigDecimal longitude
) {
    public static CycleInfoSummaryResponse of(CycleInfoSummaryInfo cycleInfoSummary) {
        return new CycleInfoSummaryResponse(cycleInfoSummary.getLatitude(),
                cycleInfoSummary.getLongitude());
    }
}
