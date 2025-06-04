package com.kbe5.rento.domain.event.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleInfo {

    @Id
    private Long id;

    private Integer sec;

    private GpsCondition gpsCondition;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Integer angle;

    private Integer speed;

    private Long sum;

    private Integer battery;
}
