package com.kbe5.api.domain.manager.controller;

import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.api.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.api.domain.manager.dto.response.ManagerDeleteResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerSignUpResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerUpdateResponse;
import com.kbe5.api.domain.manager.service.ManagerService;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.manager.entity.Manager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers")
public class ManagerControllerImpl implements ManagerController {

    private final ManagerService managerService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<ManagerSignUpResponse>> signUp(@RequestBody @Valid ManagerSignUpRequest request) {
        Manager manager = ManagerSignUpRequest.toEntity(request);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                ManagerSignUpResponse.fromEntity(managerService.signUp(manager)));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<ManagerResponse>> getManagerDetail(@PathVariable Long id) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                ManagerResponse.fromEntity(managerService.getManagerDetail(id)));
    }

    @GetMapping("/list/{companyCode}")
    public ResponseEntity<ApiResponse<List<ManagerResponse>>> getManagerList(@PathVariable String companyCode) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                ManagerResponse.fromEntity(managerService.getManagerList(companyCode)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ManagerUpdateResponse>> update(@PathVariable Long id,
                                                                     @RequestBody @Valid ManagerUpdateRequest request) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                 ManagerUpdateResponse.fromEntity(managerService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ManagerDeleteResponse>> delete(@PathVariable Long id,
                                                                     @RequestBody @Valid ManagerDeleteRequest request) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                 ManagerDeleteResponse.fromEntity(managerService.delete(id, request)));
    }

    @GetMapping("/check-loginId/{loginId}")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailableLoginId(@PathVariable String loginId) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                !managerService.isExistsLoginId(loginId));
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailableEmail(@PathVariable String email) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                !managerService.isExistsEmail(email));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails) {
        Manager manager = customManagerDetails.getManager();

        managerService.logout(manager.getId());

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "로그아웃이 성공적으로 처리되었습니다");
    }

    @PatchMapping("/fcm-token")
    public ResponseEntity<ApiResponse<String>> updateFcmToken(
            @RequestBody UpdateFcmTokenRequest tokenRequest,
            @AuthenticationPrincipal CustomManagerDetails customManagerDetails
            ){
        Long managerId = customManagerDetails.getManager().getId();

        managerService.updateFcmToken(managerId, tokenRequest);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "FCM Token이 성공적으로 저장되었습니다.");
    }
}
