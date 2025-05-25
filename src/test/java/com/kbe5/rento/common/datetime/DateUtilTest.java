package com.kbe5.rento.common.datetime;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateUtilTest {

    LocalDateTime dateTime;
    String dateString;
    @BeforeEach
    void before() {
        dateTime = LocalDateTime.of(2025, 5, 19, 14, 30, 0);
        dateString = "20250519143000";
    }

    @Test
    @DisplayName("LocalDateTime -> String 변환 성공")
    void toStrSuccess() {
        //when
        String result = DateUtil.toStr(dateTime);
        //then
        assertThat(result).isEqualTo(dateString);
    }

    @Test
    @DisplayName("LocalDateTime 이 null 값 예외 발생 테스트")
    void toStrFail() {
        //when
        Throwable thrown = catchThrowable(() -> DateUtil.toStr(null));
        //then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("LocalDateTime 값이 null 입니다.");
    }

    @Test
    @DisplayName("String -> LocalDateTime 변환 성공")
    void toLocalDateTimeSuccess() {
        //given
        String dateTimeStr = dateString;
        //when
        LocalDateTime result = DateUtil.toLocalDateTime(dateTimeStr);
        //then
        assertThat(result).isEqualTo(dateTime);
    }

    @Test
    @DisplayName("String 이 null 예외 발생 테스트 ")
    void toLocalDateTimeNull() {
        //when
        Throwable thrown = catchThrowable(() -> DateUtil.toLocalDateTime(null));
        //then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("시간 문자열이 비어있거나 null 입니다.");
    }

    @Test
    @DisplayName("String 이 \" \" 예외 발생 테스트 ")
    void toLocalDateTimeEmptyStirng() {
        //when
        Throwable thrown = catchThrowable(() -> DateUtil.toLocalDateTime(" "));
        //then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("시간 문자열이 비어있거나 null 입니다.");
    }

    // 이너 클래스로 DTO 선언
    static class SampleDto {
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime time;

        public SampleDto() {} // 기본 생성자 필요
        public SampleDto(LocalDateTime time) { this.time = time; }
        public LocalDateTime getTime() { return time; }
    }

    @Test
    @DisplayName("LocalDateTime 직렬화 테스트")
    void serializeTest() throws Exception {
        SampleDto dto = new SampleDto(dateTime);
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(dto);

        assertThat(json).isEqualTo("{\"time\":\""+dateString+"\"}");
    }

    @Test
    @DisplayName("LocalDateTime 역직렬화 테스트")
    void deserializeTest() throws Exception {
        String json = "{\"time\":\""+dateString+"\"}";
        ObjectMapper mapper = new ObjectMapper();

        SampleDto dto = mapper.readValue(json, SampleDto.class);

        assertThat(dto.getTime()).isEqualTo(dateTime);
    }
}
