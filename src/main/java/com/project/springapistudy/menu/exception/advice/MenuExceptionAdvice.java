package com.project.springapistudy.menu.exception.advice;

import com.project.springapistudy.global.dto.ErrorResponse;
import com.project.springapistudy.menu.exception.MenuIllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MenuExceptionAdvice {

    @ExceptionHandler(MenuIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationError(MenuIllegalArgumentException e) {
        return new ErrorResponse(e.getErrorCode().getErrorMessage(), e.getMessage());
    }
}
