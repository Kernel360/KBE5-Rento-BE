package com.kbe5.rento.domain.company.controller;

import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.rento.domain.company.dto.response.CompanyDeleteResponse;
import com.kbe5.rento.domain.company.dto.response.CompanyRegisterResponse;
import com.kbe5.rento.domain.company.dto.response.CompanyResponse;
import com.kbe5.rento.domain.company.dto.response.CompanyUpdateResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CompanyController {

    ResponseEntity<ApiResponse<CompanyRegisterResponse>> register(@RequestBody @Valid
                                                                  CompanyRegisterRequest request);

    ResponseEntity<ApiResponse<CompanyResponse>> getCompanyDetail(@PathVariable Long id);

    ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanyList();

    ResponseEntity<ApiResponse<CompanyUpdateResponse>> updateCompanyInfo(@PathVariable Long id,
                                                                         @RequestBody @Valid CompanyUpdateRequest request);

    ResponseEntity<ApiResponse<Boolean>> checkAvailableBizNumber(@PathVariable int bizNumber);

    ResponseEntity<ApiResponse<CompanyDeleteResponse>> companyDelete(@PathVariable Long id);
}
