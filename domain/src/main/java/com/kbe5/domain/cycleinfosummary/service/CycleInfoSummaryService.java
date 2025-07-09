package com.kbe5.domain.cycleinfosummary.service;

import com.kbe5.domain.cycleinfosummary.dto.CycleInfoSummaryInfo;

import java.util.List;

public interface CycleInfoSummaryService {

    void create(Long driveId);
    List<CycleInfoSummaryInfo> getList(Long driveId);
}
