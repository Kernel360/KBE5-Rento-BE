package com.kbe5.rento.domain.emulator.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
public class Emulator extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "vehicle_id") // 또는 mappedBy 설정
    private Vehicle vehicle;

    private String terminalId;

    private String makerId;

    private Integer packetVersion;

    private Integer deviceId;

    private LocalDateTime onTime;

    private LocalDateTime offTime;





}
