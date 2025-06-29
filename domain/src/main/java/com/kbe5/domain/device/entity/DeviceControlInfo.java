package com.kbe5.domain.device.entity;

import com.kbe5.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "device_control_info")
public class DeviceControlInfo extends BaseEntity {

    private Long mdn;

    @Column(name = "ctr_id", length = 32, nullable = false)
    private String ctrId;

    @Column(name = "ctr_cd", length = 8)
    private String ctrCd;

    @Column(name = "ctr_val", length = 16)
    private String ctrVal;
}
