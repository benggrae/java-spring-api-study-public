package com.project.springapistudy.global.exception;


import com.project.springapistudy.global.domain.ErrorCode;

public class DuplicationException extends BaseException {
    public DuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
