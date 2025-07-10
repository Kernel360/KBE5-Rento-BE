package com.kbe5.domain.cycleinfosummary.service;

import com.kbe5.domain.cycleinfosummary.dto.CycleDataSummaryInfo;

import java.util.List;

public interface CycleDataSummaryService {

    void create(Long driveId);
    List<CycleDataSummaryInfo> getList(Long driveId);
}
