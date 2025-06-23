package com.kbe5.api.domain.company.controller;

import com.kbe5.api.domain.company.service.dto.request.CompanyBiznumberRequest;
import com.kbe5.api.domain.company.service.dto.request.CompanyRegisterRequest;
import com.kbe5.api.domain.company.service.dto.request.CompanyUpdateRequest;
import com.kbe5.api.domain.company.service.dto.response.CompanyDeleteResponse;
import com.kbe5.api.domain.company.service.dto.response.CompanyRegisterResponse;
import com.kbe5.api.domain.company.service.dto.response.CompanyResponse;
import com.kbe5.api.domain.company.service.dto.response.CompanyUpdateResponse;
import com.kbe5.common.response.api.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyController {

    ResponseEntity<ApiResponse<CompanyRegisterResponse>> register(CompanyRegisterRequest request);

    ResponseEntity<ApiResponse<CompanyResponse>> getCompanyDetail(Long id);

    ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanyList();

    ResponseEntity<ApiResponse<CompanyUpdateResponse>> updateCompanyInfo(Long id, CompanyUpdateRequest request);

    ResponseEntity<ApiResponse<Boolean>> checkAvailableBizNumber(CompanyBiznumberRequest request);

    ResponseEntity<ApiResponse<CompanyDeleteResponse>> companyDelete(Long id);
}
