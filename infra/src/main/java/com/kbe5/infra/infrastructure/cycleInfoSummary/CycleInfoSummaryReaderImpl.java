package com.kbe5.infra.infrastructure.cycleInfoSummary;

import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;
import com.kbe5.domain.cycleinfosummary.service.CycleInfoSummaryReader;
import com.kbe5.infra.infrastructure.cycleInfoSummary.repository.CycleInfoSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CycleInfoSummaryReaderImpl implements CycleInfoSummaryReader {

    private final CycleInfoSummaryRepository cycleInfoSummaryRepository;

    @Override
    public List<CycleInfoSummary> getCycleInfoLSummaryListWithDrive(Long driveId) {
        return cycleInfoSummaryRepository.findAllByDriveId(driveId);
    }
}
