package com.kbe5.domain.cycleinfosummary.service;

import com.kbe5.domain.cycleinfosummary.dto.CycleDataSummaryInfo;
import com.kbe5.domain.cycleinfosummary.entity.CycleDataSummary;
import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.service.CycleDataReader;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CycleDataSummaryServiceImpl implements CycleDataSummaryService {

    private final CycleDataReader cycleDataReader;
    private final CycleDataSummaryStore cycleDataSummaryStore;
    private final CycleDataSummaryReader cycleDataSummaryReader;

    @Override
    public void create(Long driveId) {
        // 해당 운행의 주기 정보 들고옴
        List<CycleData> info = cycleDataReader.getCycleInfoListWithDrive(driveId);

        // 제일 이른 시간 찾기
        LocalDateTime baseTime = info.stream()
                .map(CycleData::getCycleInfoTime)
                .min(LocalDateTime::compareTo)
                .orElseThrow(() -> new DomainException(ErrorType.CYCLEINFO_NOT_FOUND) );

        List<CycleData> cycleData = info.stream()
                .filter(ci -> {
                    long diff = Duration.between(baseTime, ci.getCycleInfoTime()).getSeconds();
                    return diff >= 0 && diff % 5 == 0;
                })
                .sorted(Comparator.comparing(CycleData::getCycleInfoTime))
                .toList();

        List<CycleDataSummary> summary = cycleData.stream()
                .map(CycleDataSummary::new)
                .toList();

        cycleDataSummaryStore.saveAllCycleInfoSummary(summary);
    }

    @Override
    public List<CycleDataSummaryInfo> getList(Long driveId) {
        return cycleDataSummaryReader.getCycleInfoLSummaryListWithDrive(driveId)
                .stream()
                .map(CycleDataSummaryInfo::fromEntity)
                .toList();
    }
}
