package com.kbe5.api.domain.manager.controller;


import com.kbe5.api.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.api.domain.manager.dto.response.ManagerDeleteResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerSignUpResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerUpdateResponse;
import com.kbe5.common.response.api.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ManagerController {

    ResponseEntity<ApiResponse<ManagerSignUpResponse>> signUp(ManagerSignUpRequest request);

    ResponseEntity<ApiResponse<ManagerResponse>> getManagerDetail(Long id);

    ResponseEntity<ApiResponse<List<ManagerResponse>>> getManagerList(String companyCode);

    ResponseEntity<ApiResponse<ManagerUpdateResponse>> update(Long id, ManagerUpdateRequest request) ;

    ResponseEntity<ApiResponse<ManagerDeleteResponse>> delete(Long id, ManagerDeleteRequest request);

    ResponseEntity<ApiResponse<Boolean>> checkAvailableLoginId(String loginId);

    ResponseEntity<ApiResponse<Boolean>> checkAvailableEmail(String email);
}
