package com.kbe5.api.domain.company.controller;

import com.kbe5.rento.common.response.api.ApiResponse;
import com.kbe5.rento.domain.company.dto.request.CompanyBiznumberRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.rento.domain.company.dto.response.CompanyDeleteResponse;
import com.kbe5.rento.domain.company.dto.response.CompanyRegisterResponse;
import com.kbe5.rento.domain.company.dto.response.CompanyResponse;
import com.kbe5.rento.domain.company.dto.response.CompanyUpdateResponse;
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
