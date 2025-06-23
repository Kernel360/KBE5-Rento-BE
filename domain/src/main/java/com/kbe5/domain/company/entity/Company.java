package com.kbe5.domain.company.entity;


import com.kbe5.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "companies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {

    @Column(length = 50)
    private int bizNumber;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String companyCode;


    public Company(int bizNumber, String name) {
        this.bizNumber = bizNumber;
        this.name = name;
    }

    public void update(int bizNumber, String name) {
        this.bizNumber = bizNumber;
        this.name = name;
    }

    public void assignCompanyCode() {
        this.companyCode = String.format("C%s", this.getId());
    }
}
