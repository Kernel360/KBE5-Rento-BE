package com.kbe5.rento.domain.company.controller;

import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.common.apiresponse.ApiResultCode;
import com.kbe5.rento.common.apiresponse.ResEntityFactory;
import com.kbe5.rento.domain.company.dto.request.CompanyBiznumberRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.rento.domain.company.dto.response.*;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
