package com.project.springapistudy.global.domain;

public interface ErrorCode {
    default String getErrorMessage() {
        return "[" +  getCode() + "] : " + getMessage();
    }
    String getCode();
    String getMessage();

}
