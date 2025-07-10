package com.kbe5.domain.cycleinfosummary.service;

import com.kbe5.domain.cycleinfosummary.dto.CycleInfoSummaryInfo;
import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;
import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.service.CycleInfoReader;
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
public class CycleInfoSummaryServiceImpl implements CycleInfoSummaryService {

    private final CycleInfoReader cycleInfoReader;
    private final CycleInfoSummaryStore cycleInfoSummaryStore;
    private final CycleInfoSummaryReader cycleInfoSummaryReader;

    @Override
    public void create(Long driveId) {
        // 해당 운행의 주기 정보 들고옴
        List<CycleData> info = cycleInfoReader.getCycleInfoListWithDrive(driveId);

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

        List<CycleInfoSummary> summary = cycleData.stream()
                .map(CycleInfoSummary::new)
                .toList();

        cycleInfoSummaryStore.saveAllCycleInfoSummary(summary);
    }

    @Override
    public List<CycleInfoSummaryInfo> getList(Long driveId) {
        return cycleInfoSummaryReader.getCycleInfoLSummaryListWithDrive(driveId)
                .stream()
                .map(CycleInfoSummaryInfo::fromEntity)
                .toList();
    }
}
