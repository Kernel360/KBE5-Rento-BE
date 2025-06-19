package com.kbe5.rento.domain.event.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Entity
@SuperBuilder
@DiscriminatorValue("CYCLE_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleEvent extends Event{

    private Integer cycleCount;

    @Transient
    List<CycleInfo> cycleInfos;

    public void setCycleInfos(List<CycleInfo> cycleInfos) {
        this.cycleInfos = cycleInfos;
    }
}
