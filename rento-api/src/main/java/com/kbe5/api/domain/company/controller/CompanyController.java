package com.kbe5.api.domain.company.controller;

import com.kbe5.api.domain.company.dto.request.CompanyBiznumberRequest;
import com.kbe5.api.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.api.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.api.domain.company.dto.response.CompanyDeleteResponse;
import com.kbe5.api.domain.company.dto.response.CompanyRegisterResponse;
import com.kbe5.api.domain.company.dto.response.CompanyResponse;
import com.kbe5.api.domain.company.dto.response.CompanyUpdateResponse;
import com.kbe5.common.response.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "회사 서비스 API", description = "회사 등록, 수정, 삭제, 목록 조회, 상세 조회 기능을 제공합니다.")
public interface CompanyController {

    @Operation(summary = "회사 등록", description = "새로운 회사를 등록합니다.")
    @RequestBody(description = "회사 등록 정보 요청", required = true)
    ResponseEntity<ApiResponse<CompanyRegisterResponse>> register(CompanyRegisterRequest request);

    @Operation(summary = "회사 상세 정보 조회", description = "회사의 상세 정보를 조회합니다.")
    @Parameter(name = "companyId", description = "조회할 회사 ID", example = "1", required = true)
    ResponseEntity<ApiResponse<CompanyResponse>> getCompanyDetail(Long companyId);

    @Operation(summary = "회사 목록 조회", description = "회사 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanyList();

    @Operation(summary = "회사 수정", description = "회사 정보를 수정합니다.")
    @Parameter(name = "companyId", description = "수정할 회사 ID", example = "1", required = true)
    @RequestBody(description = "회사 수정 정보 요청", required = true)
    ResponseEntity<ApiResponse<CompanyUpdateResponse>> updateCompanyInfo(Long companyId, CompanyUpdateRequest request);

    @Operation(summary = "회사 사업자 번호 중복 체크", description = "사업자 번호를 중복체크합니다.")
    @RequestBody(description = "중복확인할 사업자번호", required = true)
    ResponseEntity<ApiResponse<Boolean>> checkAvailableBizNumber(CompanyBiznumberRequest request);

    @Operation(summary = "회사 삭제", description = "회사를 삭제합니다.")
    @Parameter(name = "companyId", description = "삭제할 회사 ID", example = "1", required = true)
    ResponseEntity<ApiResponse<CompanyDeleteResponse>> companyDelete(Long companyId);
}
