package com.kbe5.infra.infrastructure.cycleDataSummary;

import com.kbe5.domain.cycleinfosummary.entity.CycleDataSummary;
import com.kbe5.domain.cycleinfosummary.service.CycleDataSummaryStore;
import com.kbe5.infra.infrastructure.cycleDataSummary.repository.CycleInfoSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CycleDataSummaryStoreImpl implements CycleDataSummaryStore {

    private final CycleInfoSummaryRepository cycleInfoSummaryRepository;

    @Override
    public void saveAllCycleInfoSummary(List<CycleDataSummary> summaryList) {
        cycleInfoSummaryRepository.saveAll(summaryList);
    }
}
