package com.kbe5.api.domain.member.controller;

import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.api.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.api.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.api.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.common.response.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "회원 서비스 API", description = "회원 등록, 수정, 삭제, 목록 조회, 상세 조회 기능을 제공합니다. 매니저 로그인 후 사용 가능합니다.")
public interface MemberController {

    //사용자 추가
    @Operation(summary = "회원 등록", description = "새로운 회원을 등록합니다")
    @RequestBody(description = "회원 등록 요청 정보", required = true)
    ResponseEntity<ApiResponse<String>> register(MemberRegisterRequest request);

    //사용자 수정
    @Operation(summary = "회원 수정", description = "기존 회원을 수정합니다")
    @Parameter(name = "memberId", description = "수정할 회원의 ID", example = "1", required = true)
    @RequestBody(description = "회원 수정 요청 정보", required = true)
    ResponseEntity<ApiResponse<MemberInfoResponse>> update(CustomManagerDetails managerDetails, Long userId, MemberUpdateRequest request);

    //사용자 삭제
    @Operation(summary = "회원 삭제", description = "기존 회원을 삭제합니다")
    @Parameter(name = "memberId", description = "삭제할 회원의 ID", example = "1", required = true)
    ResponseEntity<ApiResponse<String>> delete(CustomManagerDetails managerDetails, Long userId);

    //사용자 목록 조회
    @Operation(summary = "회원 목록 조회", description = "등록되어 있는 회원의 목록을 조회합니다")
    ResponseEntity<ApiResponse<PagedModel<MemberInfoResponse>>> getUsers(
            CustomManagerDetails managerDetails,
            @Parameter(name = "position", description = "직책", example = "인턴, 사원")
            String position,
            @Parameter(name = "departmentId", description = "부서 ID", example = "1")
            Long departmentId,
            @Parameter(name = "keyword", description = "이름, 아이디, 이메일 검색 키워드", example = "홍길동")
            String keyword,
            @Parameter(hidden = true)
            Pageable pageable);

    //사용자 상세 조회
    @Operation(summary = "회원 상세 조회", description = "회원의 상세 정보을 조회합니다")
    @Parameter(name = "memberId", description = "사용자 ID", example = "1")
    ResponseEntity<ApiResponse<MemberInfoResponse>> getUser(Long memberId);

    //직책 불러오기
    @Operation(summary = "직책 불러오기", description = "직책 정보를 조회합니다")
    ResponseEntity<ApiResponse<List<String>>> getPositions();

    //아이디 중복 체크
    @Operation(summary = "아이디 중복 체크", description = "회원 등록/수정 시 아이디 중복체크를 합니다.")
    @Parameter(name = "loginId", description = "중복 검사할 아이디입니다.", example = "test")
    ResponseEntity<ApiResponse<Boolean>> checkId(CustomManagerDetails customManagerDetails, String loginId);

    //이메일 중복 체크
    @Operation(summary = "이메일 중복 체크", description = "회원 등록/수정 시 이메일 중복체크를 합니다.")
    @Parameter(name = "email", description = "중복 검사할 이메일입니다.", example = "test@test.com")
    ResponseEntity<ApiResponse<Boolean>> checkEmail(CustomManagerDetails customManagerDetails, String email);

    //전화번호 충복 체크
    @Operation(summary = "전화번호 중복 체크", description = "회원 등록/수정 시 전화번호 중복체크를 합니다.")
    @Parameter(name = "phoneNumber", description = "중복 검사할 전화번호입니다", example = "010-1234-5678")
    ResponseEntity<ApiResponse<Boolean>> checkPhoneNumber(CustomManagerDetails customManagerDetails, String phoneNumber);
}
