package com.kbe5.rento;

import com.kbe5.rento.domain.company.entity.Company;

public class CompanyTestFixtures {

    public static Company companyA(){
        return Company.builder()
                .bizNumber(123131)
                .name("testCompanyA")
                .build();
    }

    public static Company companyB(){
        return Company.builder()
                .name("testCompanyB")
                .bizNumber(555555555)
                .build();
    }
}
