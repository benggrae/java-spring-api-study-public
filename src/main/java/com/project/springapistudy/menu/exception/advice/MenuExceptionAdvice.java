package com.project.springapistudy.menu.exception.advice;

import com.project.springapistudy.global.dto.ErrorResponse;
import com.project.springapistudy.menu.exception.MenuValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MenuExceptionAdvice {

    @ExceptionHandler(MenuValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationError(MenuValidationException e) {
        return new ErrorResponse(e.getErrorCode());
    }
}
