package com.kbe5.api.domain.company.controller;


import com.kbe5.api.domain.company.dto.request.companyBizNumberRequest;
import com.kbe5.api.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.api.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.api.domain.company.dto.response.CompanyRegisterResponse;
import com.kbe5.api.domain.company.dto.response.CompanyResponse;
import com.kbe5.api.domain.company.dto.response.CompanyUpdateResponse;
import com.kbe5.api.domain.company.mapper.CompanyRequestMapper;
import com.kbe5.api.domain.company.mapper.CompanyResponseMapper;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.company.dto.CompanyCommand;
import com.kbe5.domain.company.dto.CompanyInfo;
import com.kbe5.domain.company.service.CompanyService;
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
    public ResponseEntity<ApiResponse<CompanyRegisterResponse>> register(@RequestBody @Valid CompanyRegisterRequest request) {
        CompanyCommand.Register command = CompanyRequestMapper.toCommand(request);
        CompanyInfo info = companyService.register(command);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                CompanyResponseMapper.toRegisterResponse(info));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompanyDetail(@PathVariable Long id) {
        CompanyInfo info = companyService.getCompanyDetail(id);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                CompanyResponseMapper.toResponse(info));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanyList() {
        List<CompanyInfo> infoList = companyService.getCompanyList();
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                CompanyResponseMapper.toResponseList(infoList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyUpdateResponse>> updateCompanyInfo(@PathVariable Long id,
                                                                                @RequestBody @Valid CompanyUpdateRequest request) {
        CompanyCommand.Update command = CompanyRequestMapper.toCommand(request);
        CompanyInfo info = companyService.update(id, command);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                CompanyResponseMapper.toUpdateResponse(info));
    }

    @PostMapping("/check-bizNumber")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailableBizNumber(@RequestBody @Valid companyBizNumberRequest request) {
        CompanyCommand.CheckBizNumber command = CompanyRequestMapper.toCommand(request);
        boolean available = !companyService.isAvailableBizNumber(command.getBizNumber());
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, available);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> companyDelete(@PathVariable Long id) {
        boolean result = companyService.delete(id);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, result);
    }
}
