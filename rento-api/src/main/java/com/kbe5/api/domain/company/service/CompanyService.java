package com.kbe5.api.domain.company.service;

import com.kbe5.api.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.api.domain.company.dto.response.CompanyDeleteResponse;
import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company register(Company company) {
        company = companyRepository.save(company);

        makeCompanyCode(company);

        return company;
    }

    //추후에 사용 될 함수입니다.
    public Company findByBizNumber(int bizNumber) {
        return companyRepository.findByBizNumber(bizNumber)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Company findByCompanyCode(String code) {
        return companyRepository.findByCompanyCode(code)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));
    }

    private void makeCompanyCode(Company company) {
        company.assignCompanyCode("C" + company.getId());
        companyRepository.save(company);
    }

    public Company update(Long id, CompanyUpdateRequest request) {
        Company company = companyRepository
                .findById(id).orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        company.toUpdate(request);

        return company;
    }

    public CompanyDeleteResponse delete(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        companyRepository.delete(company);

        return new CompanyDeleteResponse(company.getId(), true);
    }

    @Transactional(readOnly = true)
    public List<Company> getCompanyList() {
        return companyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Company getCompanyDetail(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));
    }

    public Boolean isExistsBizNumber(int bizNumber) {
        return companyRepository.existsByBizNumber(bizNumber);
    }
}
