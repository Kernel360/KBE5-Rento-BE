package com.kbe5.rento.domain.manager.entity;

import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "managers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager extends BaseEntity {

    private String componyCode;

    @Builder
    public Manager(String componyCode) {
        this.componyCode = componyCode;
    }
}
