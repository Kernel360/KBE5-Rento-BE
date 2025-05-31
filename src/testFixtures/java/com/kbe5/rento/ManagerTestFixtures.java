package com.kbe5.rento;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.manager.entity.Manager;

public class ManagerTestFixtures {

    public static Manager managerA(Company company){
        return Manager.builder()
                .company(company)
                .name("토렌")
                .companyCode("C1")
                .build();
    }
}
