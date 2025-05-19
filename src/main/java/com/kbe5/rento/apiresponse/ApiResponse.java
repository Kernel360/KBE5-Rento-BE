package com.kbe5.rento.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * {
 *   "status": "success",
 *   "message": "Data retrieved successfully",
 *   "data": {
 *     "id": 1,
 *     "name": "Dulanjaya Sandaruwan"
 *   },
 *   "metadata": {
 *     "page": 1,
 *     "size": 10,
 *     "total": 100
 *   }
 * }
 */
public class ApiResponse<T> {
    @JsonProperty("rstCd")
    private String resultCode;
    private String message;
    private T data;
    private Object metadata;


    @Builder
    public ApiResponse(String resultCode, String message, T data, Object metadata) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
        this.metadata = metadata;
    }
}
