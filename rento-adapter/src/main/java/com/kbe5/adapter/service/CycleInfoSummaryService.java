package com.kbe5.adapter.service;



import com.kbe5.adapter.dto.response.CycleInfoSummaryResponse;
import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;
import com.kbe5.domain.cycleinfosummary.repository.CycleInfoSummaryRepository;
import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.repository.CycleInfoRepository;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service("commonCycleinfoSummaryService")
@RequiredArgsConstructor
public class CycleInfoSummaryService {

    private final CycleInfoRepository cycleInfoRepository;
    private final CycleInfoSummaryRepository cycleInfoSummaryRepository;

    public void create(Long driveId) {
        // 해당 운행의 주기 정보 들고옴
        List<CycleInfo> info = cycleInfoRepository.findAllByDriveId(driveId);

        // 제일 이른 시간 찾기
        LocalDateTime baseTime = info.stream()
                .map(CycleInfo::getCycleInfoTime)
                .min(LocalDateTime::compareTo)
                .orElseThrow(() -> new DomainException(ErrorType.CYCLEINFO_NOT_FOUND) );

        // 걍 인포 찾고 5초 뒤에거 찾고 찾고 찾고 하면되는거 아닌가?
        List<CycleInfo> cycleInfo = info.stream()
                .filter(ci -> {
                    long diff = Duration.between(baseTime, ci.getCycleInfoTime()).getSeconds();
                    return diff >= 0 && diff % 5 == 0;
                })
                .sorted(Comparator.comparing(CycleInfo::getCycleInfoTime))
                .toList();

        List<CycleInfoSummary> summary = cycleInfo.stream()
                .map(CycleInfoSummary::new)
                .toList();

        cycleInfoSummaryRepository.saveAll(summary);
    }

    public List<CycleInfoSummaryResponse> getList(Long driveId) {
        return cycleInfoSummaryRepository.findAllByDriveId(driveId)
                .stream()
                .map(summary -> {
                    try {
                        return new CycleInfoSummaryResponse(
                                summary.getLatitude(),
                                summary.getLongitude()
                        );
                    } catch (Exception e) {
                        throw new DomainException(ErrorType.CYCLEINFO_NOT_FOUND);
                    }
                })
                .toList();
    }
}
