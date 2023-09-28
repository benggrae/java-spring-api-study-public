package com.project.springapistudy.global.exception.advice;


import com.project.springapistudy.global.dto.ErrorResponse;
import com.project.springapistudy.global.exception.DuplicationException;
import com.project.springapistudy.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ErrorResponse("-1", e.getMessage());
    }

    @ExceptionHandler(DuplicationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse duplicationExceptionHandler(DuplicationException e) {
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(NotFoundException e) {
        return new ErrorResponse(e.getErrorCode());
    }
}
