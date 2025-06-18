package com.kbe5.rento.domain.firebase.controller;

import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.common.apiresponse.ApiResultCode;
import com.kbe5.rento.common.apiresponse.ResEntityFactory;
import com.kbe5.rento.domain.firebase.dto.TokenNotificationRequest;
import com.kbe5.rento.domain.firebase.service.FcmService;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class FcmControllerImpl implements FcmController {

    private final FcmService fcmService;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<String>> sendMessageToken(@RequestBody TokenNotificationRequest tokenNotificationRequest,
                                                                @AuthenticationPrincipal CustomManagerDetails customManagerDetails) {

        Manager manager = customManagerDetails.getManager();

        fcmService.send(tokenNotificationRequest, manager);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "메세지 보내는 것을 성공하였습니다");
    }
}
