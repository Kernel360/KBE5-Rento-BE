package com.kbe5.rento.domain.company.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.rento.domain.company.dto.response.*;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

        return CompanyRegisterResponse.from(company);
    }

    //추후에 사용 될 함수입니다.
    public Company findByBizNumber(int bizNumber) {
        return companyRepository.findByBizNumber(bizNumber)
                .orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));
    }

    @Transactional(readOnly = true)
    public Company findByCompanyCode(String code) {
        return companyRepository.findByCompanyCode(code)
                .orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));
    }

    private void makeCompanyCode(Company company) {
        company.assignCompanyCode("C" + company.getId());
        companyRepository.save(company);
    }

    public CompanyUpdateResponse update(Long id, CompanyUpdateRequest request) {

        Company company = companyRepository
                .findById(id).orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        company.toUpdate(request);

        return CompanyUpdateResponse.from(company);
    }

    public CompanyDeleteResponse delete(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        companyRepository.delete(company); // 에러 방식 수정 후 리펙토링 예정입니다.

        return new CompanyDeleteResponse(company.getId(), true);
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> getCompanyList() {

        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(CompanyResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CompanyResponse getCompanyDetail(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        return CompanyResponse.from(company);
    }

    public Boolean isExistsBizNumber(int bizNumber) {
        return companyRepository.existsByBizNumber(bizNumber);
    }
}
