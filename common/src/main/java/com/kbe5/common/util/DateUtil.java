package com.kbe5.common.util;

import static com.kbe5.common.exception.ErrorType.BLANK_DATE_STRING;
import static com.kbe5.common.exception.ErrorType.NULL_LOCAL_DATE_TIME;

import com.kbe5.common.exception.DomainException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter CYCLE_EVENT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    private DateUtil() {
        //인스턴스 불가능한 Util 클래스 입니다.
        throw new AssertionError();
    }

    public static String toStr(LocalDateTime time) {
        if (time == null) {
            throw new DomainException(NULL_LOCAL_DATE_TIME);
        }
        return time.format(FORMATTER);
    }

    public static LocalDateTime toLocalDateTime(String text) {
        if (isBlank(text)) {
            throw new DomainException(BLANK_DATE_STRING);
        }
        return LocalDateTime.parse(text, FORMATTER);
    }

    public static LocalDateTime toEventLocalDateTime(String text) {
        if (text == null) {
            throw new DomainException(BLANK_DATE_STRING);
        }
        if (text.trim().equals("")) {
            return null;
        }
        return LocalDateTime.parse(text, FORMATTER);
    }

    public static LocalDateTime toCycleInfoEventLocalDateTime(String text) {
        if (isBlank(text)) {
            throw new DomainException(BLANK_DATE_STRING);
        }
        return LocalDateTime.parse(text, CYCLE_EVENT_FORMATTER);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

}
