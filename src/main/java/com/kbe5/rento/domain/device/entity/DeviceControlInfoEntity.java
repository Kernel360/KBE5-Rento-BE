package com.kbe5.rento.domain.device.entity;

import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "device_control_info")
public class DeviceControlInfoEntity extends BaseEntity {

    @Column(name = "ctr_id", length = 32, nullable = false)
    private String ctrId;

    @Column(name = "ctr_cd", length = 8)
    private String ctrCd;

    @Column(name = "ctr_val", length = 16)
    private String ctrVal;
}