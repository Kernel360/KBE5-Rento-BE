package com.kbe5.rento.domain.member.controller;

import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.rento.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.rento.domain.member.dto.response.MemberInfoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberController {

    //사용자 추가
    ResponseEntity<ApiResponse<String>> register(MemberRegisterRequest request);
    //사용자 수정
    ResponseEntity<ApiResponse<MemberInfoResponse>> update(CustomManagerDetails managerDetails, Long userId, MemberUpdateRequest request);
    //사용자 삭제
    ResponseEntity<ApiResponse<String>> delete(CustomManagerDetails managerDetails, Long userId);
    //사용자 목록 조회
    ResponseEntity<ApiResponse<List<MemberInfoResponse>>> getUsers(String company);
    //사용자 상세 조회
    ResponseEntity<ApiResponse<MemberInfoResponse>> getUser(Long userId);

}
