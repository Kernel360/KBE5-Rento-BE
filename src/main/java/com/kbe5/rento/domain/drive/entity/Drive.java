package com.kbe5.rento.domain.drive.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private DriveType dirveType;

    private String startLocation;
    private String endLocation;
    private boolean isStart;

    private String companyCode;

    @Builder
    public Drive(Member member, Vehicle vehicle, DriveType dirveType, String startLocation,
                 String endLocation, String companyCode) {
        this.member = member;
        this.vehicle = vehicle;
        this.dirveType = dirveType;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.isStart = false;
        this.companyCode = companyCode;
    }

    public void driveStart(){
       this.isStart = true;
       this.startDate = LocalDateTime.now();
    }

    public void driveEnd(){
        this.isStart = false;
        this.endDate = LocalDateTime.now();
    }
}
