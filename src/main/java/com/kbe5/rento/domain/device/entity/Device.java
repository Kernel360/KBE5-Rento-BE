package com.kbe5.rento.domain.device.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
public class Device extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "vehicle_id") // 또는 mappedBy 설정
    private Vehicle vehicle;

    private String terminalId;

    private Integer makerId;

    private Integer packetVersion;

    private Integer deviceId;

    //얘는 어떻게할지 일단 고민할것
    private String deviceFirmWareVersion;
}
