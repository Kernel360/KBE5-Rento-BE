package com.kbe5.rento.domain.company;

import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "companies")
public class Company extends BaseEntity {

    private Long companyNumber;
    private String name;
    private String companyCode;
}
