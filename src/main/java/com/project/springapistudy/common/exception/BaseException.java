package com.project.springapistudy.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    public abstract String getMessage();
}
