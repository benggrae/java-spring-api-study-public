package com.project.springapistudy.global.exception;

public interface ErrorCode {
    default String getErrorMessage() {
        return "[" +  getCode() + "] : " + getMessage();
    }
    String getCode();
    String getMessage();

}
