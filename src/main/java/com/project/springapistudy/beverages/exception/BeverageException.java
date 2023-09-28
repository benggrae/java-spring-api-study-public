package com.project.springapistudy.beverages.exception;

import com.project.springapistudy.beverages.constants.BeverageMessages;

public class BeverageException extends RuntimeException {
    private final ErrorResponse response;

    public BeverageException(BeverageMessages messages) {
        this.response = new ErrorResponse(messages.getKey(), messages.getName());
    }

    public BeverageException(final long code, final String message) {
        this.response = new ErrorResponse(code, message);
    }

    @Override
    public String getMessage() {
        return this.response.getMessage();
    }

    public ErrorResponse getResponse() {
        return response;
    }
}
