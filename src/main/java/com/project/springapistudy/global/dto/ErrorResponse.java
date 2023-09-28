package com.project.springapistudy.global.dto;

import com.project.springapistudy.global.domain.ErrorCode;

public record ErrorResponse(
    String code,
    String message
) {
    public ErrorResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }
}
