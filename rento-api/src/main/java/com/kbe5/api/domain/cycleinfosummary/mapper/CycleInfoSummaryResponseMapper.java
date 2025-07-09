package com.kbe5.api.domain.cycleinfosummary.mapper;

import com.kbe5.api.domain.cycleinfosummary.dto.CycleInfoSummaryResponse;
import com.kbe5.domain.cycleinfosummary.dto.CycleInfoSummaryInfo;
import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;
import org.springframework.stereotype.Component;

@Component
public class CycleInfoSummaryResponseMapper {

    public CycleInfoSummaryResponse toCycleInfoSummary(CycleInfoSummaryInfo cycleInfoSummary) {
        return CycleInfoSummaryResponse.of(cycleInfoSummary);
    }
}
