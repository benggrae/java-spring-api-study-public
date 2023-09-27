package com.project.springapistudy.common.handler;

import com.project.springapistudy.common.util.ResponseData;
import com.project.springapistudy.common.exception.DuplicationMenuException;
import com.project.springapistudy.common.exception.IdNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData.ApiResult<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> collect = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseData.fail(collect.toString());
    }

}
