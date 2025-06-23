package com.kbe5.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class VehicleNumberValidator implements ConstraintValidator<VehicleNumber, String> {

    private static final Pattern PATTERN = Pattern.compile("^(?:\\d{2}|\\d{3})[가-힣]\\d{4}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // null/blank 허용 안하려면 false 반환
        }
        // 공백 제거 후 검증
        String normalized = value.replaceAll("\\s+", "");

        return PATTERN.matcher(normalized).matches();
    }
}
