package com.kbe5.rento.domain.member.controller;

import com.kbe5.rento.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.rento.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.rento.domain.member.dto.response.MemberInfoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberController {

    //사용자 추가
    void register(MemberRegisterRequest request);
    //사용자 수정
    void update(Long userId, MemberUpdateRequest request);
    //사용자 삭제
    ResponseEntity<String> delete(Long userId);
    //사용자 목록 조회
    ResponseEntity<List<MemberInfoResponse>> getUsers(String company);
    //사용자 상세 조회
    ResponseEntity<MemberInfoResponse> getUser(Long userId);

}
