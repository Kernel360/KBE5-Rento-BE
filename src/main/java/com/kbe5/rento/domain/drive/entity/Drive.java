package com.kbe5.rento.domain.drive.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "drives")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drive extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    private String startLocation;
    private String endLocation;

    private Long distance;

    @Enumerated(EnumType.STRING)
    private DriveStatus driveStatus;

    private Long mdn;

    @Builder
    private Drive(Member member, Vehicle vehicle, DriveType driveType, String startLocation,
                 String endLocation, LocalDateTime startDate, LocalDateTime endDate) {
        this.member = member;
        this.vehicle = vehicle;
        this.driveType = driveType;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = 0L;
        this.driveStatus = DriveStatus.READY;
    }

    public void driveStart(){
       this.driveStatus = DriveStatus.DRIVING;
    }

    public void driveEnd(){
        this.driveStatus = DriveStatus.COMPLETED;
    }

    public void addDistance(Long distance){
        this.distance = distance;
    }

    public void addMdn(Long mdn){
        this.mdn = mdn;
    }
}
