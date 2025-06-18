package com.kbe5.rento.domain.firebase.controller;

import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.domain.firebase.dto.TokenNotificationRequest;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import org.springframework.http.ResponseEntity;

public interface FcmController {
    ResponseEntity<ApiResponse<String>> sendMessageToken(TokenNotificationRequest tokenNotificationRequest, CustomManagerDetails customManagerDetails);
}
