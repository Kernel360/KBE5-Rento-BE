package com.kbe5.infra.infrastructure.cycleInfoSummary;

import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;
import com.kbe5.domain.cycleinfosummary.service.CycleInfoSummaryStore;
import com.kbe5.infra.infrastructure.cycleInfoSummary.repository.CycleInfoSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CycleInfoSummaryStoreImpl implements CycleInfoSummaryStore {

    private final CycleInfoSummaryRepository cycleInfoSummaryRepository;

    @Override
    public void saveAllCycleInfoSummary(List<CycleInfoSummary> summaryList) {
        cycleInfoSummaryRepository.saveAll(summaryList);
    }
}
