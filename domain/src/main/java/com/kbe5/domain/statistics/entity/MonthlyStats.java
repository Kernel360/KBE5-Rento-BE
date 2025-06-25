package com.kbe5.domain.statistics.entity;

import com.kbe5.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "monthly_stats",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"company_code", "month"})}
)
public class MonthlyStats extends BaseEntity {
    @Column(name = "company_code", nullable = false)
    private String companyCode;

    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private int month;

    private long totalDistance; //총 운행 거리
    private long totalDrivingTime; //총 운행 시간
    private int totalDrivingCnt; //총 운행 횟수
    private double avgSpeed; //평균 속도

    private double businessRatio; //업무용 비율
    private double commuteRatio; //통근용 비율
    private double nonBusinessRatio; //비업무용 비율

    @Builder
    private MonthlyStats(String companyCode, int year, int month, long totalDistance,
                         long totalDrivingTime, int totalDrivingCnt, double avgSpeed, int maxSpeed,
                         double businessRatio, double commuteRatio, double nonBusinessRatio) {
        this.companyCode = companyCode;
        this.year = year;
        this.month = month;
        this.totalDistance = totalDistance;
        this.totalDrivingTime = totalDrivingTime;
        this.totalDrivingCnt = totalDrivingCnt;
        this.avgSpeed = avgSpeed;
        this.businessRatio = businessRatio;
        this.commuteRatio = commuteRatio;
        this.nonBusinessRatio = nonBusinessRatio;
    }

    public void update(MonthlyStats newStats) {
        this.totalDistance = newStats.getTotalDistance();
        this.totalDrivingTime = newStats.getTotalDrivingTime();
        this.totalDrivingCnt = newStats.getTotalDrivingCnt();
        this.avgSpeed = newStats.getAvgSpeed();
        this.businessRatio = newStats.getBusinessRatio();
        this.commuteRatio = newStats.getCommuteRatio();
        this.nonBusinessRatio = newStats.getNonBusinessRatio();
    }
}
