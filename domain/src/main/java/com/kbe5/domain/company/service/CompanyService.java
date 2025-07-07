package com.kbe5.domain.company.service;

import com.kbe5.domain.company.dto.CompanyCommand;
import com.kbe5.domain.company.dto.CompanyInfo;

import java.util.List;

public interface CompanyService {

    CompanyInfo register(CompanyCommand.Register command);
    CompanyInfo update(Long id, CompanyCommand.Update command);
    boolean delete(Long id);
    CompanyInfo getCompanyDetail(Long id);
    List<CompanyInfo> getCompanyList();
    boolean isExistsBizNumber(int bizNumber);
    CompanyInfo findByCompanyCode(String code);
}
