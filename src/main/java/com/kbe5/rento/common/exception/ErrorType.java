package com.kbe5.rento.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorType {
    // DEPARTMENT
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "부서를 찾을 수 없습니다"),
    DUPLICATE_DEPARTMENT_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 부서 이름입니다."),
    ALREADY_MEMBER(HttpStatus.BAD_REQUEST, "해당 부서에 소속된 직원이 있어 삭제할 수 없습니다."),
    NOT_AUTHORIZED(HttpStatus.BAD_REQUEST, "해당 권한이 없습니다"),

    // VALIDATION
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "값을 잘못 입력했습니다."),

    // MANAGER
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "관리자를 찾을 수 없습니다."),

    // COMPANY
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "업체를 찾을 수 없습니다."),

    //MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATE_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "이미 존재하는 전화번호입니다."),
    INVALID_POSITION(HttpStatus.BAD_REQUEST, "존재하지 않은 직책입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다." ),
    DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다." ),

    // SECURITY
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 없거나 잘못된 형식입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    FAILED_LOGIN(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),

    // VEHICLE
    SAME_VEHICLE_NUMBER(HttpStatus.BAD_REQUEST, "이미 등록된 차량 번호입니다."),
    VEHICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "차량을 찾을 수 없습니다"),

    // Drive
    DRIVE_NOT_FOUND(HttpStatus.NOT_FOUND, "운행을 찾지 못했습니다."),
    USER_VEHICLE_COMPANY_MISMATCH(HttpStatus.BAD_REQUEST, "업체가 일치하지 않습니다"),

    // Geofence
    GEOFENCE_NOT_FOUND(HttpStatus.NOT_FOUND, "지오펜스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
