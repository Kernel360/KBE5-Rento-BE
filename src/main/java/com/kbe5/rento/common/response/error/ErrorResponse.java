package com.kbe5.rento.common.response.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorResponse {

    public static void SendError(HttpServletResponse response, ErrorType errorType) throws IOException {
        DomainException domainException = new DomainException(errorType);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(domainException.getStatus().value());

        String body = new ObjectMapper().writeValueAsString(domainException.toResponse());

        response.getWriter().write(body);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
