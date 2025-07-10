package com.kbe5.domain.cycleinfosummary.service;

import com.kbe5.domain.cycleinfosummary.entity.CycleDataSummary;

import java.util.List;

public interface CycleDataSummaryReader {

    List<CycleDataSummary> getCycleInfoLSummaryListWithDrive(Long driveId);
}
