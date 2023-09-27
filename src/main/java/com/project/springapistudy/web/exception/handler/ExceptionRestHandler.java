package com.project.springapistudy.web.exception.handler;

import com.project.springapistudy.util.ResponseData;
import com.project.springapistudy.web.exception.DuplicationMenuException;
import com.project.springapistudy.web.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestHandler {

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData.ApiResult<?> idNotFoundException(IdNotFoundException e) {
        return ResponseData.fail(e.getMessage());
    }

    @ExceptionHandler(DuplicationMenuException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData.ApiResult<?> idNotFoundException(DuplicationMenuException e) {
        return ResponseData.fail(e.getMessage());
    }

}
