package com.project.springapistudy.beverages.exception;

public class ErrorResponse {
    private long code;
    private String message;

    public ErrorResponse(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
