package com.kbe5.rento.domain.member.controller;

import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.common.apiresponse.ApiResultCode;
import com.kbe5.rento.common.apiresponse.ResEntityFactory;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.rento.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.rento.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.entity.Position;
import com.kbe5.rento.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, memberService.getMemberList(companyCode)
                .stream()
                .map(MemberInfoResponse::from)
                .toList()
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
}
