package com.procsin.API.Model;

import org.springframework.http.HttpStatus;

public class ApiError {

    public HttpStatus status;
    public String message;
    public String apiErrorCode;

    public ApiError(HttpStatus status, String message, String apiErrorCode) {
        this.status = status;
        this.message = message;
        this.apiErrorCode = apiErrorCode;
    }
}
