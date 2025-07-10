package com.kbe5.infra.infrastructure.cycleDataSummary;

import com.kbe5.domain.cycleinfosummary.entity.CycleDataSummary;
import com.kbe5.domain.cycleinfosummary.service.CycleDataSummaryReader;
import com.kbe5.infra.infrastructure.cycleDataSummary.repository.CycleInfoSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CycleDataSummaryReaderImpl implements CycleDataSummaryReader {

    private final CycleInfoSummaryRepository cycleInfoSummaryRepository;

    @Override
    public List<CycleDataSummary> getCycleInfoLSummaryListWithDrive(Long driveId) {
        return cycleInfoSummaryRepository.findAllByDriveId(driveId);
    }
}
