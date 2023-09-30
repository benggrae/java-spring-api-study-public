package com.project.springapistudy.menu.exception;

import com.project.springapistudy.global.exception.BaseException;
import com.project.springapistudy.global.domain.ErrorCode;

public class MenuValidationException extends BaseException {
    public MenuValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
