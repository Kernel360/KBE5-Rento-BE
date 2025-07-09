package com.kbe5.domain.cycleinfosummary.service;

import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;

import java.util.List;

public interface CycleInfoSummaryStore {

    void saveAllCycleInfoSummary(List<CycleInfoSummary> summaryList);
}
