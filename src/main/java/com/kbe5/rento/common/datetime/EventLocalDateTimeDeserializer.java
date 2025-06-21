package com.kbe5.rento.common.datetime;

import static com.kbe5.rento.common.exception.ErrorType.INVALID_DATE_FORMAT;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kbe5.rento.common.exception.DomainException;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    // yyyyMMddHHmm
    private static final int CYCLE_INFO_EVENT_LENGTH = 12;

    // yyyyMMddHHmmss
    private static final int DEFAULT_EVENT_LENGTH = 14;

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {

        String text = p.getText();

        if (text.length() == CYCLE_INFO_EVENT_LENGTH) {
            return DateUtil.toCycleInfoEventLocalDateTime(text);
        }
        if (text.length() == DEFAULT_EVENT_LENGTH) {
            return DateUtil.toEventLocalDateTime(text);
        }
        throw new DomainException(INVALID_DATE_FORMAT);
    }
}
