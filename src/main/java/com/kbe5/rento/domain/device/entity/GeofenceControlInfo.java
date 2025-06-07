package com.kbe5.rento.domain.device.entity;

import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "geofence_control_info")
public class GeofenceControlInfo extends BaseEntity {

    private Long mdn;

    @Column(name = "geo_ctr_id", length = 32, nullable = false)
    private Long geoCtrId;

    @Column(name = "up_val", length = 8)
    private Integer upVal;

    @Column(name = "geo_grp_id", length = 8)
    private Integer geoGrpId;

    @Column(name = "geo_evt_tp", length = 8)
    private Short geoEvtTp;

    @Column(name = "geo_range", length = 8)
    private Integer geoRange;

    @Column(name = "lat", length = 16)
    private BigDecimal lat;

    @Column(name = "lon", length = 16)
    private BigDecimal lon;

    @Column(name = "on_time", length = 14)
    private LocalDateTime onTime;

    @Column(name = "off_time", length = 14)
    private LocalDateTime offTime;

    @Column(name = "store_tp", length = 4)
    private Short storeTp;
}
