package com.kbe5.rento.domain.device.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OnEventRequestTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 올바른_값이면_200_반환() throws Exception {
        String json = """
        {
            "mdn": 12345678901,
            "tid": "A001",
            "mid": "6",
            "pv": 5,
            "did": 1,
            "onTime": "20240523100000",
            "offTime": "",
            "gcd": "A",
            "lat": 37.123456,
            "lon": 127.123456,
            "ang": 90,
            "spd": 50,
            "sum": 100000
        }
        """;

        OnEventRequest request = objectMapper.readValue(json, OnEventRequest.class);

        assertThat(request.mobileDeviceNumber()).isEqualTo(12345678901L);
        assertThat(request.onTime()).isEqualTo("20240523100000");
        assertThat(request.offTime()).isEqualTo("");
        assertThat(request.gpsCondition()).isEqualTo(GpsCondition.NORMAL);
        assertThat(request.latitude()).isEqualTo(new BigDecimal("37.123456"));
        assertThat(request.currentAccumulatedDistance()).isEqualTo(100000);

    }
}
