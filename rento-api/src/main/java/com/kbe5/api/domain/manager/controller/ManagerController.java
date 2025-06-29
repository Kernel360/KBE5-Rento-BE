package com.kbe5.api.domain.manager.controller;


import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.api.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.api.domain.manager.dto.response.ManagerDeleteResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerSignUpResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerUpdateResponse;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.infra.firebase.dto.UpdateFcmTokenRequest;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "매니저 서비스 API", description = "매니저 등록, 수정, 삭제, 목록 조회, 상세 조회 기능을 제공합니다.")
public interface ManagerController {

    @Operation(summary = "매니저 등록", description = "새로운 매니저을 등록합니다")
    @RequestBody(
            description = "매니저 등록 요청 정보",
            required = true
    )
    ResponseEntity<ApiResponse<ManagerSignUpResponse>> signUp(ManagerSignUpRequest request);

    @Operation(summary = "매니저 상세 정보 조회", description = "매니저 상세 정보를 조회합니다.")
    @Parameter(name = "managerId", description = "조회할 매니저 ID", example = "1", required = true)
    ResponseEntity<ApiResponse<ManagerResponse>> getManagerDetail(Long managerId);

    @Operation(summary = "매니저 목록 조회", description = "매니저 목록을 조회합니다.")
    @Parameter(name = "companyCode", description = "조회할 회사 코드입니다.")
    ResponseEntity<ApiResponse<List<ManagerResponse>>> getManagerList(String companyCode);

    @Operation(summary = "매니저 정보 수정", description = "매니저 정보를 수정합니다.")
    @Parameter(name = "managerId", description = "수정할 매니저 ID", example = "1", required = true)
    @RequestBody(description = "매니저 수정 정보 요청", required = true)
    ResponseEntity<ApiResponse<ManagerUpdateResponse>> update(Long managerId, ManagerUpdateRequest request) ;

    @Operation(summary = "매니저 삭제", description = "매니저를 삭제합니다.")
    @Parameter(name = "managerId", description = "삭제할 매니저 ID", example = "1", required = true)
    @RequestBody(description = "매니저 삭제 정보 요청", required = true)
    ResponseEntity<ApiResponse<ManagerDeleteResponse>> delete(Long id, ManagerDeleteRequest request);

    @Operation(summary = "아이디 중복 체크", description = "매니저 등록 시 아이디 중복체크를 합니다")
    @Parameter(name = "loginId", description = "중복체크할 아이디", example = "test", required = true)
    ResponseEntity<ApiResponse<Boolean>> checkAvailableLoginId(String loginId);

    @Operation(summary = "이메일 중복 체크", description = "매니저 등록 시 이메일 중복체크를 합니다")
    @Parameter(name = "email", description = "중복체크할 이메일", example = "test", required = true)
    ResponseEntity<ApiResponse<Boolean>> checkAvailableEmail(String email);

    @Operation(summary = "로그아웃", description = "매니저 로그아웃입니다.")
    ResponseEntity<ApiResponse<String>> logout(CustomManagerDetails customManagerDetails);

    @Hidden
    ResponseEntity<ApiResponse<String>> updateFcmToken(UpdateFcmTokenRequest tokenRequest, CustomManagerDetails customManagerDetails);
}
