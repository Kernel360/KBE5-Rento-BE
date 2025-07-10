package com.kbe5.domain.company.service;

import com.kbe5.domain.company.entity.Company;

public interface CompanyStore {
    Company store(Company company);
    void delete(Company company);
    void makeCompanyCode(Company company);
}
