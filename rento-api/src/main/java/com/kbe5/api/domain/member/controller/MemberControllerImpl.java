package com.kbe5.api.domain.member.controller;

import com.kbe5.api.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.api.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.api.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.api.domain.member.mapper.MemberRequestMapper;
import com.kbe5.api.domain.member.mapper.MemberResponseMapper;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.member.dto.MemberCommand;
import com.kbe5.domain.member.dto.MemberInfo;
import com.kbe5.domain.member.entity.Position;
import com.kbe5.domain.member.service.MemberService;
import com.kbe5.infra.security.details.CustomManagerDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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
    private final MemberRequestMapper requestMapper;
    private final MemberResponseMapper responseMapper;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<String>> register(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @RequestBody @Validated MemberRegisterRequest request) {
        MemberCommand.Register command = requestMapper.toRegisterCommand(request);
        MemberInfo info = memberService.registerMember(command);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, info.getName()+ " 성공적으로 등록되었습니다.");
    }

    @Override
    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> update(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable Long memberId,
            @RequestBody @Validated MemberUpdateRequest request
    ) {
        MemberCommand.Update command = requestMapper.toUpdateCommand(request);
        MemberInfo info = memberService.updateMember(memberId, command);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, responseMapper.toResponse(info));
    }

    @Override
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<String>> delete(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable Long memberId
    ) {
        memberService.delete(memberId);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "성공적으로 삭제되었습니다.");
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<PagedModel<MemberInfoResponse>>> getUsers(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String keyword,
            Pageable pageable) {

        Manager manager = customManagerDetails.getManager();
        Position newPosition = position != null ? Position.fromValue(position) : null;

        Page<MemberInfo> memberInfos = memberService.getMembers(manager, newPosition, departmentId, keyword, pageable);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                new PagedModel<>(responseMapper.toResponseList(memberInfos)));
    }

    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> getUser(@PathVariable Long memberId) {
        return ResEntityFactory.toResponse(
                ApiResultCode.SUCCESS, responseMapper.toResponse(memberService.getMember(memberId))
        );
    }

    @GetMapping("/positions")
    public ResponseEntity<ApiResponse<List<String>>> getPositions() {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, Position.getPositions());
    }

    //아이디 중복 체크
    @GetMapping("/check-id/{loginId}")
    public ResponseEntity<ApiResponse<Boolean>> checkId(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable String loginId) {
        String companyCode = customManagerDetails.getManager().getCompanyCode();

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, !memberService.isExistLoginId(companyCode, loginId));
    }

    //이메일 중복 체크
    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkEmail(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable String email) {
        String companyCode = customManagerDetails.getManager().getCompanyCode();

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, !memberService.isExistEmail(companyCode, email));
    }

    //전화번호 중복 체크
    @GetMapping("/check-phone/{phoneNumber}")
    public ResponseEntity<ApiResponse<Boolean>> checkPhoneNumber(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails,
            @PathVariable String phoneNumber) {
        String companyCode = customManagerDetails.getManager().getCompanyCode();

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, !memberService.isExistPhoneNumber(companyCode, phoneNumber));
    }
}
