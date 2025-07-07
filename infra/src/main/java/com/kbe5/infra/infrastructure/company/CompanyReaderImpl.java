package com.kbe5.infra.infrastructure.company;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.company.repository.CompanyRepository;
import com.kbe5.domain.company.service.CompanyReader;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyReaderImpl implements CompanyReader {

    private final CompanyRepository companyRepository;

    @Override
    public Company findByBizNumber(int bizNumber) {
        return companyRepository.findByBizNumber(bizNumber)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));
    }

    @Override
    public Company findByCompanyCode(String code) {
        return companyRepository.findByCompanyCode(code)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));
    }

    @Override
    public Company findById(Long id) {
        return companyRepository
                .findById(id).orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public boolean existsByBizNumber(int bizNumber) {
        return companyRepository.existsByBizNumber(bizNumber);
    }
}
