package com.kbe5.adapter.dto.response;


import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;

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
