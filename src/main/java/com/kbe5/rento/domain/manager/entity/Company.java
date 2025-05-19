package com.kbe5.rento.domain.manager.entity;

import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Company extends BaseEntity {

    private Long companyNumber;
    private String name;
    private String companyCode;
}
