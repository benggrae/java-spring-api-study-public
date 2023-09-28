package com.project.springapistudy.global.exception;


public class DuplicationException extends RuntimeException {
    public DuplicationException(ErrorCode error) {
        super(error.getErrorMessage());

    }
}
