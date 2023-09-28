package com.project.springapistudy.commons.exceptionhandler;

import com.project.springapistudy.beverages.exception.BeverageBadRequestException;
import com.project.springapistudy.beverages.exception.BeverageNotFountException;
import com.project.springapistudy.beverages.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = Controller.class)
public class SirenOrderExceptionController {

    @ExceptionHandler(BeverageNotFountException.class)
    public ResponseEntity<ErrorResponse> exception(BeverageNotFountException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getResponse());
    }

    @ExceptionHandler(BeverageBadRequestException.class)
    public ResponseEntity<ErrorResponse> exception(BeverageBadRequestException e) {
        return ResponseEntity.badRequest().body(e.getResponse());
    }
}
