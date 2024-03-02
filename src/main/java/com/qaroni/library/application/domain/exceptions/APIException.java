package com.qaroni.library.application.domain.exceptions;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {

    private final HttpStatus httpStatus;
    public APIException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
