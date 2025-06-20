package com.kbe5.api.domain.company.controller;


import com.kbe5.api.domain.company.dto.request.CompanyBiznumberRequest;
import com.kbe5.api.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.api.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.api.domain.company.dto.response.CompanyDeleteResponse;
import com.kbe5.api.domain.company.dto.response.CompanyRegisterResponse;
import com.kbe5.api.domain.company.dto.response.CompanyResponse;
import com.kbe5.api.domain.company.dto.response.CompanyUpdateResponse;
import com.kbe5.api.domain.company.service.CompanyService;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyControllerImpl implements CompanyController {

    private final CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CompanyRegisterResponse>> register(@RequestBody @Valid
                                                                         CompanyRegisterRequest request) {
        Company company = CompanyRegisterRequest.toEntity(request);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                CompanyRegisterResponse.fromEntity(companyService.register(company)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompanyDetail(@PathVariable Long id) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS
                ,CompanyResponse.fromEntity(companyService.getCompanyDetail(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanyList() {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS
                ,CompanyResponse.fromEntity(companyService.getCompanyList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyUpdateResponse>> updateCompanyInfo(@PathVariable Long id,
                                                                                @RequestBody @Valid CompanyUpdateRequest request) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS
                ,CompanyUpdateResponse.fromEntity(companyService.update(id, request)));
    }

    @PostMapping("/check-bizNumber")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailableBizNumber(@RequestBody CompanyBiznumberRequest request) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                !companyService.isExistsBizNumber(request.biznumber()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyDeleteResponse>> companyDelete(@PathVariable Long id) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS
                 ,companyService.delete(id));
    }
}
