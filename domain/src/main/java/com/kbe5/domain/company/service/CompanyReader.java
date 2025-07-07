package com.kbe5.domain.company.service;

import com.kbe5.domain.company.entity.Company;

import java.util.List;

public interface CompanyReader {
    Company findByBizNumber(int bizNumber);
    Company findByCompanyCode(String code);
    Company findById(Long id);
    List<Company> findAll();
    boolean existsByBizNumber(int bizNumber);
}
