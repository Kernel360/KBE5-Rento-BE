package com.kbe5.api.domain.statistics.service;

import com.kbe5.api.domain.statistics.dto.MonthlyStatsRequest;
import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import com.kbe5.domain.drive.entity.DriveType;
import com.kbe5.domain.drive.repository.DriveRepository;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.statistics.entity.MonthlyStats;
import com.kbe5.domain.statistics.repository.MonthlyStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonthlyService {
    private final MonthlyStatsRepository monthlyStatsRepository;
    private final DriveRepository driveRepository;

    @Transactional(readOnly = true)
    public Optional<MonthlyStats> getStats(MonthlyStatsRequest monthlyStatsRequest) {
        String companyCode = monthlyStatsRequest.companyCode();
        int year = monthlyStatsRequest.year();
        int month = monthlyStatsRequest.month();

        return monthlyStatsRepository.findByCompanyCodeAndYearAndMonth(companyCode, year, month);
    }

    @Transactional
    public void generateMonthlyStats() {
        log.info("월벌 통계 생성 시작");

        //운행 완료된 데이터 조회
        List<Drive> completedDrives = driveRepository.findByDriveStatus(DriveStatus.COMPLETED);

        if(completedDrives.isEmpty()) {
            log.info("완료된 운행 데이터가 없습니다");
            return;
        }

        //회사별로 그룹화
        Map<String, List<Drive>> drivesByCompany = completedDrives.stream().collect(Collectors.groupingBy(
                drive -> drive.getMember().getCompanyCode()
        ));

        drivesByCompany.forEach(this::generateStatsForCompany);

        log.info("월별 통계 생성 완료");
    }

    private void generateStatsForCompany(String companyCode, List<Drive> drives) {
        log.info("회사 {} 월별 통계 생성 시작", companyCode);

        Map<YearMonth, List<Drive>> driveByYearMonth = drives.stream()
                .filter(drive -> drive.getStartDate() != null)
                .collect(Collectors.groupingBy(drive ->
                        YearMonth.from(drive.getStartDate())));

        driveByYearMonth.forEach((yearMonth, monthlyDrives) -> {
            MonthlyStats newStats = calculateMonthlyStats(companyCode, yearMonth, monthlyDrives);

            Optional<MonthlyStats> existingStats = monthlyStatsRepository.findByCompanyCodeAndYearAndMonth(
                    companyCode, yearMonth.getYear(), yearMonth.getMonthValue()
            );

            YearMonth now = YearMonth.now();

            if (existingStats.isPresent()) {
                if(yearMonth.equals(now)) {
                    //현재 달이면 덮어쓰기
                    MonthlyStats stats = existingStats.get();
                    stats.update(newStats);
                    log.info("회사 {}의 {}/{} 통계 갱신 완료", companyCode, yearMonth.getYear(), yearMonth.getMonthValue());
                } else {
                    //과거 달이면 건너뛰기
                    log.info("회사 {} 의 {}/{} 통계 이미 존재(과거), 갱신 안함", companyCode, yearMonth.getYear(), yearMonth.getMonthValue());
                }
            } else {
                // 없으면 생성 (현재든 과거든)
                monthlyStatsRepository.save(newStats);
                log.info("회사 {} 의 {}/{} 통계 생성 완료", companyCode, yearMonth.getYear(), yearMonth.getMonthValue());
            }
        });
    }

    private MonthlyStats calculateMonthlyStats(String companyCode, YearMonth yearMonth, List<Drive> monthlyDrives) {
        long totalDistance = monthlyDrives.stream()
                .mapToLong(Drive::getDistance).sum();

        long totalDrivingTime = monthlyDrives.stream()
                .mapToLong(drive -> Duration.between(drive.getStartDate(), drive.getEndDate()).toMinutes())
                .sum();

        int totalDrivingCnt = monthlyDrives.size();

        double totalDistanceKm = totalDistance / 1000.0;
        double totalDrivingTimeHours = totalDrivingTime / 60.0;

        double avgSpeed = totalDrivingTimeHours > 0 ? totalDistanceKm / totalDrivingTimeHours : 0.0;
        avgSpeed = Math.round(avgSpeed * 10) / 10.0; // 소수점 1자리 반올림

        long businessCnt = monthlyDrives.stream()
                .mapToLong(drive -> DriveType.BUSINESS.equals(drive.getDriveType()) ? 1 : 0)
                .sum();

        long commuteCnt = monthlyDrives.stream()
                .mapToLong(drive -> DriveType.COMMUTE.equals(drive.getDriveType()) ? 1 : 0)
                .sum();

        long nonBusinessCnt = totalDrivingCnt - businessCnt - commuteCnt;

        double businessRatio = totalDrivingCnt > 0 ? (double) businessCnt / totalDrivingCnt * 100 : 0;
        double commuteRatio = totalDrivingCnt > 0 ? (double) commuteCnt / totalDrivingCnt * 100 : 0;
        double nonBusinessRatio = totalDrivingCnt > 0 ? (double) nonBusinessCnt / totalDrivingCnt * 100 : 0;

        return MonthlyStats.builder()
                .companyCode(companyCode)
                .year(yearMonth.getYear())
                .month(yearMonth.getMonthValue())
                .totalDistance(totalDistance)
                .totalDrivingTime(totalDrivingTime)
                .totalDrivingCnt(totalDrivingCnt)
                .avgSpeed(avgSpeed)
                .businessRatio(businessRatio)
                .commuteRatio(commuteRatio)
                .nonBusinessRatio(nonBusinessRatio)
                .build();
    }
}
