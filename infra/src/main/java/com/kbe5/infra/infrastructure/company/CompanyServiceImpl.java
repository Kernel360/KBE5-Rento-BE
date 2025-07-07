package com.kbe5.infra.infrastructure.company;

import com.kbe5.domain.company.dto.CompanyCommand;
import com.kbe5.domain.company.dto.CompanyInfo;
import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.company.service.CompanyReader;
import com.kbe5.domain.company.service.CompanyService;
import com.kbe5.domain.company.service.CompanyStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyReader companyReader;
    private final CompanyStore companyStore;

    @Override
    public CompanyInfo register(CompanyCommand.Register command) {
        Company company = command.toEntity();
        company = companyStore.store(company);

        companyStore.makeCompanyCode(company);

        return CompanyInfo.fromEntity(company);
    }

    @Override
    public CompanyInfo update(Long id, CompanyCommand.Update command) {
        Company company = companyReader.findById(id);

        command.applyTo(company);

        return CompanyInfo.fromEntity(company);
    }

    @Override
    public boolean delete(Long id) {
        Company company = companyReader.findById(id);

        companyStore.delete(company);

        return true;
    }

    @Override
    public CompanyInfo getCompanyDetail(Long id) {
        Company company = companyReader.findById(id);

        return CompanyInfo.fromEntity(company);
    }

    @Override
    public List<CompanyInfo> getCompanyList() {
        List<Company> companies = companyReader.findAll();

        return companies.stream().map(CompanyInfo::fromEntity).toList();
    }

    @Override
    public boolean isExistsBizNumber(int bizNumber) {
        return !companyReader.existsByBizNumber(bizNumber);
    }

    @Override
    public CompanyInfo findByCompanyCode(String code) {
        Company company = companyReader.findByCompanyCode(code);

        return CompanyInfo.fromEntity(company);
    }
}
