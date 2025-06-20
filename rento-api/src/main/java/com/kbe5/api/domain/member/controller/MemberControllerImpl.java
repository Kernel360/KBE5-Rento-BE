package com.kbe5.api.domain.member.controller;


import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.api.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.api.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.api.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.api.domain.member.service.MemberService;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberControllerImpl implements MemberController {

    private final MemberService memberService;


    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<String>> register(@RequestBody @Validated MemberRegisterRequest request) {
        Member member = memberService.register(MemberRegisterRequest.toEntity(request),request.departmentId());

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, member.getName()+ " 성공적으로 등록되었습니다.");
    }

    @Override
    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> update(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable Long memberId,
            @RequestBody @Validated MemberUpdateRequest request
    ) {
        Manager manager = customManagerDetails.getManager();

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, memberService.update(manager, request ,memberId));
    }

    @Override
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<String>> delete(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable Long memberId
    ) {
        Manager manager = customManagerDetails.getManager();

        memberService.delete(manager, memberId);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "성공적으로 삭제되었습니다.");
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberInfoResponse>>> getUsers(@RequestParam String companyCode) {
        log.info("company code is : {}", companyCode);
        List<Member> memberList = memberService.getMemberList(companyCode);
        log.info("member size : {}",memberList.size());
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, memberList
                .stream()
                .map(MemberInfoResponse::from)
                .toList()
//        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, memberService.getMemberList(companyCode)
//                .stream()
//                .map(MemberInfoResponse::from)
//                .toList()
        );
    }

    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> getUser(@PathVariable Long memberId) {
        return ResEntityFactory.toResponse(
                ApiResultCode.SUCCESS, MemberInfoResponse.from(memberService.getMember(memberId)
                )
        );
    }

    @GetMapping("/positions")
    public ResponseEntity<ApiResponse<List<String>>> getPositions() {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, Position.getPositions());
    }

    //아이디 중복 체크
    @GetMapping("/check-id/{loginId}")
    public ResponseEntity<ApiResponse<Boolean>> checkId(
            @PathVariable String loginId) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, !memberService.isExistLoginId(loginId));
    }

    //이메일 중복 체크
    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkEmail(@PathVariable String email) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, !memberService.isExistEmail(email));
    }

    //전화번호 중복 체크
    @GetMapping("/check-phone/{phoneNumber}")
    public ResponseEntity<ApiResponse<Boolean>> checkPhoneNumber(@PathVariable String phoneNumber) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, !memberService.isExistPhoneNumber(phoneNumber));
    }
}
