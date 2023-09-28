package com.project.springapistudy.menu.exception;

import com.project.springapistudy.global.exception.ErrorCode;
import lombok.Getter;

public class MenuIllegalArgumentException extends IllegalArgumentException {
    @Getter
    private final ErrorCode errorCode;

    public MenuIllegalArgumentException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
