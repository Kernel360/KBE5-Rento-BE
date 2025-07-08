package com.kbe5.infra.infrastructure.company;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.infra.infrastructure.company.repository.CompanyRepository;
import com.kbe5.domain.company.service.CompanyStore;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyStoreImpl implements CompanyStore {

    private final CompanyRepository companyRepository;

    @Override
    public Company store(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void delete(Company company) {

        try {
            companyRepository.delete(company);
        } catch (Exception e) {
            throw new DomainException(ErrorType.COMPANY_NOT_FOUND);
        }
    }

    public void makeCompanyCode(Company company) {
        company.assignCompanyCode("C" + company.getId());
        companyRepository.save(company);
    }
}
