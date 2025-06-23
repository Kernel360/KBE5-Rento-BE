package com.kbe5.domain.event.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleInfoId implements Serializable {


    private Long mdn;

    private LocalDateTime cycleInfoTime;
}
