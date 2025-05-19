package com.kbe5.rento.domain.manager.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department extends BaseEntity {

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Vehicle> vehicles;

    private String name;
    
    @Builder
    public Department(Company company, List<Vehicle> vehicles, String name) {
        this.company = company;
        this.vehicles = vehicles;
        this.name = name;
    }
}
