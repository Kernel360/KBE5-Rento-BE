package com.kbe5.rento.common.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String toStr(LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException("LocalDateTime 값이 null 입니다.");
        }
        return time.format(FORMATTER);
    }

    public static LocalDateTime toLocalDateTime(String text) {
        if (isBlank(text)) {
            throw new IllegalArgumentException("시간 문자열이 비어있거나 null 입니다.");
        }
        return LocalDateTime.parse(text, FORMATTER);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

}
