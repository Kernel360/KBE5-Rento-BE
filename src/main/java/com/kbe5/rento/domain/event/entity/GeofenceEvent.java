package com.kbe5.rento.domain.event.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@SuperBuilder
@DiscriminatorValue("GEOFENCE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GeofenceEvent extends Event {

    int geoGrpId;

    int geoPid;

    int evtVal;
}
