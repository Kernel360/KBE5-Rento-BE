package com.kbe5.rento.domain.company.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.dto.request.CompanyDeleteRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.rento.domain.company.dto.response.*;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyRegisterResponse register(CompanyRegisterRequest request) {

        Company company = Company.builder()
                .name(request.name())
                .bizNumber(request.bizNumber())
                .build();

        company = companyRepository.save(company);

        makeCompanyCode(company);

        return new CompanyRegisterResponse(company.getName(), company.getCompanyCode());
    }

    public Company findByBizNumber(int bizNumber) {
        return companyRepository.findByBizNumber(bizNumber).orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));
    }

    public Company findByCompanyCode(String code) {
        return companyRepository.findByCompanyCode(code).orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));
    }

    private void makeCompanyCode(Company company) {
        company.setCompanyCode("C" + company.getId());
        companyRepository.save(company);
    }

    public CompanyUpdateResponse update(CompanyUpdateRequest request) {

        Company updatedCompany = companyRepository.findById(request.id()).orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        updatedCompany.toUpdate(request);

        companyRepository.save(updatedCompany);

        return new CompanyUpdateResponse(request.id(), updatedCompany.getBizNumber(), updatedCompany.getName());
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }

    public CompanyListResponse getCompanyList() {

        List<Company> companies = companyRepository.findAll();

        return new CompanyListResponse(companies);
    }

    public CompanyDetailResponse getCompanyDetail(Long id) {

        Company company = companyRepository.findById(id).orElseThrow();

        return new CompanyDetailResponse(company.getId(), company.getName(), company.getBizNumber(), company.getCompanyCode());
    }
}
