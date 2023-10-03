package com.project.springapistudy.global.exception;

import com.project.springapistudy.global.domain.ErrorCode;


public class NotFoundException extends BaseException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
