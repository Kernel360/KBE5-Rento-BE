package com.kbe5.rento.domain.company.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Company extends BaseEntity {

    @Column(length = 50)
    private int bizNumber;

    @Column(length = 30)
    private String name;

    @Setter
    @Column(length = 30)
    private String companyCode;

    @Builder
    private Company(int bizNumber, String name) {
        this.bizNumber = bizNumber;
        this.name = name;
    }

    public void toUpdate(CompanyUpdateRequest request) {
        this.bizNumber = request.bizNumber();
        this.name = request.name();
    }
}
