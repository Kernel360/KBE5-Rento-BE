package com.kbe5.rento.domain.event.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Entity
@SuperBuilder
@DiscriminatorValue("ON_OFF")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnOffEvent extends Event {

    LocalDateTime onTime;

    LocalDateTime offTime;

    private Integer batteryVolt;
}
