package com.kbe5.rento.common.apiresponse;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApiResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("ApiResponse(metadata 포함 X) 직렬화 테스트")
    void apiResponseTest() throws Exception {
        ApiResponse<String> response = ApiResponse.of(ApiResultCode.SUCCESS, "hello");

        String json = objectMapper.writeValueAsString(response);
        assertThat(json).contains("\"resultCode\":\"SUCCESS\"");
        assertThat(json).doesNotContain("metadata");
    }

    @Test
    @DisplayName("ApiResponse(metadata 포함 o) 직렬화 테스트")
    void apiResponseTestWithMetadata() throws Exception {
        Map<String, Object> meta = Map.of("total", 100);

        ApiResponse<String> response = ApiResponse.of(ApiResultCode.SUCCESS, "hello", meta);

        String json = objectMapper.writeValueAsString(response);
        assertThat(json).contains("\"resultCode\":\"SUCCESS\"");
        assertThat(json).contains("\"metadata\":{\"total\":100}");
    }
}
