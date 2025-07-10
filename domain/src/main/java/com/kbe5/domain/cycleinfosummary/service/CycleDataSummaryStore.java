package com.kbe5.domain.cycleinfosummary.service;

import com.kbe5.domain.cycleinfosummary.entity.CycleDataSummary;

import java.util.List;

public interface CycleDataSummaryStore {

    void saveAllCycleInfoSummary(List<CycleDataSummary> summaryList);
}
