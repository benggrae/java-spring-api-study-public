package com.project.springapistudy.menu.exception;

import com.project.springapistudy.global.exception.BaseException;
import com.project.springapistudy.global.domain.ErrorCode;

public class MenuIllegalArgumentException extends BaseException {
    public MenuIllegalArgumentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
