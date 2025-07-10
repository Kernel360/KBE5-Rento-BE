package com.kbe5.api.domain.cycleDatasummary.mapper;

import com.kbe5.api.domain.cycleDatasummary.dto.CycleDataSummaryResponse;
import com.kbe5.domain.cycleinfosummary.dto.CycleDataSummaryInfo;
import org.springframework.stereotype.Component;

@Component
public class CycleDataSummaryResponseMapper {

    public CycleDataSummaryResponse toCycleInfoSummary(CycleDataSummaryInfo cycleInfoSummary) {
        return CycleDataSummaryResponse.of(cycleInfoSummary);
    }
}
