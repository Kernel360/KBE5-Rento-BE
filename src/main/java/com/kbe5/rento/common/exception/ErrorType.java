package com.kbe5.rento.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorType {

    SUCCESS("000", "Success"),

    // INVALID ACCESS
    INVALID_ACCESS_PATH("100", "Invalid access path."),
    WRONG_APPROACH("101", "This is the wrong approach."),
    CONTENT_TYPE_ERROR("102", "Content-Type error."),
    CONTENT_LENGTH_ERROR("103", "Content-Length error."),
    ACCEPT_ERROR("104", "ACCEPT error."),
    CACHE_CONTROL_ERROR("105", "Cache-Control error."),
    ACCEPT_ENCODING_ERROR("106", "Accept-Encoding error."),
    TIMESTAMP_ERROR("107", "Timestamp error."),
    TUID_ERROR("108", "TUID error."),
    MISSING_KEY_VERSION("109", "Missing Key-Version."),
    NOT_JSON_HEADER_TYPE("110", "Not json header type."),

    // TOKEN ERRORS
    MISSING_TOKEN("200", "Missing Token."),
    INVALID_TOKEN("201", "Invalid Token."),
    UNUSABLE_TOKEN("202", "Unusable Token."),

    // PROTOCOL ERRORS
    PROTOCOL_FORMAT_ERROR("300", "This is a protocol format error."),
    REQUIRED_PARAMETER_ERROR("301", "Required parameter error."),
    NO_SEARCH_RESULTS("302", "There are no search results."),
    DECRYPTION_ERROR("303", "Decryption error."),
    MISMATCHED_MDN("304", "Mismatched MDN."),

    // PROCESSING ERRORS
    DATA_PROCESSING_ERROR("400", "An error occurred while processing data."),

    // SYSTEM ERRORS
    UNDEFINED_ERROR("500", "An undefined error has occurred.");

    private final String code;
    private final String message;
}
