package com.kbe5.rento.domain.device.entity;

import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "geofence_control_info")
public class GeofenceControlInfoEntity extends BaseEntity {

    @Column(name = "geo_ctr_id", length = 32, nullable = false)
    private String geoCtrId;

    @Column(name = "up_val", length = 8)
    private String upVal;

    @Column(name = "geo_grp_id", length = 8)
    private String geoGrpId;

    @Column(name = "geo_evt_tp", length = 8)
    private String geoEvtTp;

    @Column(name = "geo_range", length = 8)
    private String geoRange;

    @Column(name = "lat", length = 16)
    private String lat;

    @Column(name = "lon", length = 16)
    private String lon;

    @Column(name = "on_time", length = 14)
    private String onTime;

    @Column(name = "off_time", length = 14)
    private String offTime;

    @Column(name = "store_tp", length = 4)
    private String storeTp;
}
