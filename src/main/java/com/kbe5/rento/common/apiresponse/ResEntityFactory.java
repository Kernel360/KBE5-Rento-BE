package com.kbe5.rento.common.apiresponse;

import com.kbe5.rento.common.response.api.ApiResponse;
import com.kbe5.rento.common.response.api.ApiResultCode;
import org.springframework.http.ResponseEntity;

public class ResEntityFactory {

    private void ResFactory() {
        throw new AssertionError();
    }

    public static <T> ResponseEntity<ApiResponse<T>> toResponse(ApiResultCode apiResultCode, T data) {
        return toResponse(apiResultCode, data, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> toResponse(ApiResultCode apiResultCode, T data, Object metadata) {
        return ResponseEntity.status(apiResultCode.getHttpStatus())
            .body(ApiResponse.of(apiResultCode, data, metadata));
    }
}
