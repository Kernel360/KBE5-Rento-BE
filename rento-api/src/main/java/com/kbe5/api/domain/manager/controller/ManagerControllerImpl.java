package com.kbe5.api.domain.manager.controller;

import com.kbe5.api.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.api.domain.manager.dto.response.ManagerDeleteResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerSignUpResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerUpdateResponse;
import com.kbe5.api.domain.manager.mapper.ManagerRequestMapper;
import com.kbe5.api.domain.manager.mapper.ManagerResponseMapper;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.manager.dto.ManagerCommand;
import com.kbe5.domain.manager.dto.ManagerInfo;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.manager.service.ManagerService;
import com.kbe5.api.domain.manager.dto.request.UpdateFcmTokenRequest;
import com.kbe5.infra.security.details.CustomManagerDetails;
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
        ManagerCommand.Register command = ManagerRequestMapper.toCommand(request);
        ManagerInfo info = managerService.signUpManager(command);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                ManagerResponseMapper.toSignUpResponse(info));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<ManagerResponse>> getManagerDetail(@PathVariable Long id) {
        ManagerInfo info = managerService.getManagerInfo(id);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                ManagerResponseMapper.toResponse(info));
    }

    @GetMapping("/list/{companyCode}")
    public ResponseEntity<ApiResponse<List<ManagerResponse>>> getManagerList(@PathVariable String companyCode) {
        List<ManagerInfo> infoList = managerService.getManagerList(companyCode);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                ManagerResponseMapper.toResponseList(infoList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ManagerUpdateResponse>> update(@PathVariable Long id,
                                                                     @RequestBody @Valid ManagerUpdateRequest request) {
        ManagerCommand.Update command = ManagerRequestMapper.toCommand(request);
        ManagerInfo info = managerService.updateManager(id, command);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                 ManagerResponseMapper.toUpdateResponse(info));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ManagerDeleteResponse>> delete(@PathVariable Long id,
                                                                     @RequestBody @Valid ManagerDeleteRequest request) {
        ManagerCommand.Delete command = ManagerRequestMapper.toCommand(request);
        boolean result = managerService.deleteManager(id, command);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                 ManagerResponseMapper.toDeleteResponse(result));
    }

    @GetMapping("/check-loginId/{loginId}")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailableLoginId(@PathVariable String loginId) {

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                !managerService.isExistLoginId(loginId));
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailableEmail(@PathVariable String email) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                !managerService.isExistEmail(email));
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
        ManagerCommand.UpdateFcmToken command = ManagerRequestMapper.toCommand(tokenRequest);
        managerService.updateFcmToken(managerId, command);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "FCM Token이 성공적으로 저장되었습니다.");
    }
}
