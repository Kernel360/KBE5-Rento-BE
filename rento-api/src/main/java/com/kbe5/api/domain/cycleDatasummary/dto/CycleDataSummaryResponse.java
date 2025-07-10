package com.kbe5.api.domain.cycleDatasummary.dto;


import com.kbe5.domain.cycleinfosummary.dto.CycleDataSummaryInfo;

import java.math.BigDecimal;

public record CycleDataSummaryResponse(
         BigDecimal latitude,
         BigDecimal longitude
) {
    public static CycleDataSummaryResponse of(CycleDataSummaryInfo cycleInfoSummary) {
        return new CycleDataSummaryResponse(cycleInfoSummary.getLatitude(),
                cycleInfoSummary.getLongitude());
    }
}
