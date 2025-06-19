package com.kbe5.rento.common.response.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {

    private String resultCode;
    private String message;
    private T data;
    private Object metadata;

    @Builder(access = AccessLevel.PRIVATE)
    private ApiResponse(String resultCode, String message, T data, Object metadata) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
        this.metadata = metadata;
    }

    public static <T> ApiResponse<T> of(ApiResultCode code, T data) {
        return of(code, data, null);
    }

    public static <T> ApiResponse<T> of(ApiResultCode code, T data, Object metadata) {
        return ApiResponse.<T>builder()
            .resultCode(code.getResultCode())
            .message(code.getMessage())
            .data(data)
            .metadata(metadata)
            .build();
    }

}
